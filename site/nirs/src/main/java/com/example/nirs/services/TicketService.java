package com.example.nirs.services;

import com.example.nirs.models.Session;
import com.example.nirs.models.Ticket;
import com.example.nirs.models.User;
import com.example.nirs.repository.SessionRepository;
import com.example.nirs.repository.TicketRepository;
import com.example.nirs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    /*
    0 - ok
    1 - непредвиденная ошибка
    2 - указанного id нет в списке
    3 - дата сеанса уже прошла
    */
    public char deleteTicket(Integer ticketId){
        try {
            if (!ticketRepository.findById(ticketId).isPresent())
                return '2';
            Ticket ticket = ticketRepository.findById(ticketId).get();
            if (ticket.getSessionId().getStartTime().getTime() < Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+03:00"))).getTime())
                return '3';
            ticket.getBuyerId().setBalance(ticket.getBuyerId().getBalance() + ticket.getSessionId().getCostOfOneTicket());
            ticketRepository.delete(ticket);
            return '0';
        }
        catch (Exception e) {
            return '1';
        }
    }

    public Set<Ticket> getUsersTickets(Integer userId){
        return ticketRepository.getUsersTickets(userId);
    }

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public char createTickets(Integer sessionId, Integer userId, Integer[] places){
        try{
        if (!sessionRepository.findById(sessionId).isPresent())
            return '2';
        if (!userRepository.findById(userId).isPresent())
            return '2';
        User curUser = userRepository.findById(userId).get();
        Session curSession = sessionRepository.findById(sessionId).get();
        for (int i=0;i< places.length;i+=2){
            if (curUser.getUsername() != "ADMIN")
                curUser.setBalance(curUser.getBalance()-curSession.getCostOfOneTicket());
            Ticket ticket = new Ticket(places[i],places[i+1],curUser,curSession);
            curSession.getTickets().add(ticket);
            ticketRepository.save(ticket);
        }
        userRepository.save(curUser);
        sessionRepository.save(curSession);
        return '0';
        } catch (Exception e){
            return '3';
        }
    }
}
