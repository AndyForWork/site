package com.example.nirs.services;

import com.example.nirs.models.Hall;
import com.example.nirs.models.Session;
import com.example.nirs.repository.CinemaRepository;
import com.example.nirs.repository.HallRepository;
import com.example.nirs.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class  HallService {
    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SessionService sessionService;

    public List<Hall> getAllHalls(){
        return hallRepository.findAll();
    }

    public Hall getHallById(Integer hallId){
        if (!hallRepository.findById(hallId).isPresent())
            return null;
        return hallRepository.findById(hallId).get();
    }

    public Hall addHall(Hall newHall, Integer cinemaId){
        if (!cinemaRepository.findById(cinemaId).isPresent())
            return null;
        if (hallRepository.findByName(newHall.getName()).isPresent())
            return null;
        newHall.setCinema(cinemaRepository.findById(cinemaId).get());
        hallRepository.save(newHall);
        return newHall;
    }

    public char updateHall(Hall newHall, Integer prevHallId){
        try {
            if (!hallRepository.findById(prevHallId).isPresent())
                return '2';
            if (hallRepository.findByName(newHall.getName()).isPresent() && hallRepository.findByName(newHall.getName()).get().getId() != prevHallId)
                return '3';
            Hall prevHall = hallRepository.findById(prevHallId).get();
            prevHall.copyData(newHall);
            hallRepository.save(prevHall);
            return '0';
        } catch (Exception e){
            return '1';
        }
    }

    /*
    0 - ok
    1 - непредвиденная ошибка;
    2 - указанного id нет в списке
    3 - есть уже купленные билеты
    */
    public char delHall(Integer hallId){
        try {
            if (!hallRepository.findById(hallId).isPresent())
                return '2';
            Hall hall = hallRepository.findById(hallId).get();
            for (Session session : hall.getSessionSet()) {
                if (!session.getTickets().isEmpty())
                    return '3';
            }
            for (Session session : hall.getSessionSet()) {
                char res = sessionService.delSession(session.getId());
                if (res != '0')
                    return res;
            }
            hall.setCinema(null);
            hallRepository.delete(hall);
            return '0';
        } catch(Exception e) {
            return '1';
        }
    }



}
