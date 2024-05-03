package com.example.nirs.services;

import com.example.nirs.models.Film;
import com.example.nirs.models.Session;
import com.example.nirs.repository.FilmRepository;
import com.example.nirs.repository.HallRepository;
import com.example.nirs.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {


    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private FilmRepository filmRepository;

    public List<Session> getAllSession(){
        return sessionRepository.findAll();
    }

    public Session getById(Integer sessionId){
        if (!sessionRepository.findById(sessionId).isPresent())
            return null;
        return sessionRepository.findById(sessionId).get();
    }

    public Session addNewSession(Session newSession){
        //проверка на пересечение времены промежутков

        for (Session session: sessionRepository.findAll()){
            if (session.getHall().getId() == newSession.getHall().getId())
                if (session.getStartTime().getTime() <= newSession.getStartTime().getTime()+ newSession.getDuration() * 1000*60 &&
            session.getStartTime().getTime() + session.getDuration()*1000*60 >= newSession.getStartTime().getTime())
                return null;
        }
        sessionRepository.save(newSession);
        return newSession;
    }

    /*
0 - ok
1 - непредвиденная ошибка;
2 - указанного id нет в списке
3 - есть уже купленные билеты
*/
    public char delSession(Integer sessionId){
        try {
            if (!sessionRepository.findById(sessionId).isPresent())
                return '2';
            Session session = sessionRepository.findById(sessionId).get();
            if (!session.getTickets().isEmpty())
                return '3';
            sessionRepository.delete(session);
            return '0';
        } catch ( Exception e){
            return '1';
        }
    }

    public char updateSession(Session session, Integer sessionId){
        try{
            if (!sessionRepository.findById(sessionId).isPresent())
                return '2';
            Session curSession = sessionRepository.findById(sessionId).get();
            curSession.copyData(session);
            sessionRepository.save(curSession);
            return '0';
        }catch(Exception e) {
            return '1';
        }
    }

}
