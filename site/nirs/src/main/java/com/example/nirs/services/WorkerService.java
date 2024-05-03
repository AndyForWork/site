package com.example.nirs.services;

import com.example.nirs.models.*;
import com.example.nirs.repository.RoleRepository;
import com.example.nirs.repository.UserRepository;
import com.example.nirs.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Worker> getAllWorkers(){
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Integer id){
        if (workerRepository.findById(id).isPresent())
            return workerRepository.findById(id).get();
        else
            return null;
    }

    public Worker updateWorkerInfo(Worker worker, Integer id){
        if (!workerRepository.findById(id).isPresent())
            return null;
        Worker prevWorkerInfo = workerRepository.findById(id).get();

        prevWorkerInfo.copyInfo(worker);
        workerRepository.save(prevWorkerInfo);
        return prevWorkerInfo;
    }

    public Worker createWorkerFromUser(Worker worker){
        if (!userRepository.findById(worker.getId()).isPresent())
            return null;
        if (workerRepository.findById(worker.getId()).isPresent())
            return null;
        workerRepository.save(worker);
        User u = userRepository.findById(worker.getId()).get();
        u.addAuthority(roleRepository.findByAuthority("WORKER").get());
        userRepository.save(u);
        return worker;
    }

    /*
0 - ok
1 - непредвиденная ошибка;
2 - указанного id нет в списке
3 - нет базового работника
*/
    public char delWorker(Integer id){
        try {
            if (!workerRepository.findById(id).isPresent())
                return '2';
            if (!workerRepository.findByFirstName("ADMIN_NAME").isPresent())
                return '3';
            Worker deleatingWorker = workerRepository.findById(id).get();
            Worker basicWorker = workerRepository.findByFirstName("ADMIN_NAME").get();
            for (WorkShift shift : deleatingWorker.getShifts()) {
                shift.getWorkers().remove(deleatingWorker);
            }
            deleatingWorker.getShifts().clear();
            workerRepository.save(deleatingWorker);
            User u = userRepository.findById(id).get();
            u.delAuthority(roleRepository.findByAuthority("WORKER").get());
            userRepository.save(u);
            workerRepository.delete(deleatingWorker);
            return '0';
        } catch(Exception e){
            return '1';
        }
    }

}
