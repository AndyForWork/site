package com.example.nirs.services;

import com.example.nirs.models.Cinema;
import com.example.nirs.models.Hall;
import com.example.nirs.models.Worker;
import com.example.nirs.repository.CinemaRepository;
import com.example.nirs.repository.HallRepository;
import com.example.nirs.repository.SessionRepository;
import com.example.nirs.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public List<Cinema> getAllCinemas(){
        return cinemaRepository.findAll();
    }

    public Cinema getCinemaById(Integer id){
        if (!cinemaRepository.findById(id).isPresent())
            return null;
        return cinemaRepository.findById(id).get();
    }

    public Cinema addCinema(Cinema cinema){
        if (cinemaRepository.findByName(cinema.getName()).isPresent())
            return null;
        cinemaRepository.save(cinema);
        return cinemaRepository.findByName(cinema.getName()).get();
    }

    public Cinema updateCinema(Cinema cinema, Integer id){
        if (!cinemaRepository.findById(id).isPresent())
            return null;
        Cinema prevCinema = cinemaRepository.findById(id).get();
        prevCinema.copyData(cinema);
        cinemaRepository.save(prevCinema);
        return prevCinema;
    }

    /*
    0 - ok
    1 - непредвиденная ошибка;
    2 - указанного id нет в списке
    3 - есть сеансы на которые уже куплены билеты, которые еще не прошли
    */
    public char deleteCinema(Integer cinemaId){
        try {
            if (!cinemaRepository.findById(cinemaId).isPresent())
                return '2';
            if (cinemaRepository.countTicketsInCinema(cinemaId) != 0)
                return '3';
            workerRepository.setDefaultCinemaToWorkersFromCinemaWithId(cinemaId);
            sessionRepository.setHallToDefaultInSessionInCinemasWithId(cinemaId);
            hallRepository.deleteHallInCinemaWithId(cinemaId);
            Cinema curCinema = cinemaRepository.findById(cinemaId).get();
            curCinema.getWorkers().clear();
            cinemaRepository.delete(curCinema);
            return '0';
        }
        catch (Exception e) {
            return '1';
        }
    }

    public char UpdateInfo(Cinema cinema, Integer cinemaId){
        try {
            if (!cinemaRepository.findById(cinemaId).isPresent())
                return '2';
            Cinema curCinema = cinemaRepository.findById(cinemaId).get();
            curCinema.copyData(cinema);
            cinemaRepository.save(curCinema);
            return '0';
        }catch(Exception e){
            return '1';
        }
    }




}
