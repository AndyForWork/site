package com.example.nirs.services;

import com.example.nirs.models.WorkShift;
import com.example.nirs.models.Worker;
import com.example.nirs.repository.WorkShiftRepository;
import com.example.nirs.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WorkShiftService {
    @Autowired
    private WorkShiftRepository workShiftRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public List<WorkShift> getAllWorkShift() {
        return workShiftRepository.findAll();
    }

    public WorkShift getSomeWorkShiftByMidTime(Date midTime) {

        WorkShift startShift = null;
        WorkShift endShift = null;

        for (WorkShift workShift : workShiftRepository.findAll()) {
            if (checkDateTimeEquals(workShift.getEndTime(), midTime))
                startShift = workShift;
            if (checkDateTimeEquals(workShift.getStartTime(), midTime))
                endShift = workShift;
        }
        if (startShift == null && endShift == null)
            return null;
        else if (startShift != null && endShift != null) {
            Set<Worker> workers = new HashSet<>(startShift.getWorkers());
            for (Worker worker : endShift.getWorkers())
                workers.add(worker);
            return new WorkShift(startShift.getId(), workers, startShift.getStartTime(), endShift.getEndTime());
        }
        else if (startShift != null)
            return startShift;
        else
            return endShift;

    }

  /*
    0 - ok
1 - непредвиденная ошибка;
2 - указанного id нет в списке
3 - id одного из работников отсутствует
*/
    public char updateWorkShift(Integer[] workerIdes, Integer workShiftId){
        try {
            if (!workShiftRepository.findById(workShiftId).isPresent())
                return '2';
            for (Integer id : workerIdes) {
                if (!workerRepository.findById(id).isPresent())
                    return '3';
            }
            WorkShift curWorkShift = workShiftRepository.findById(workShiftId).get();
            Integer newId = getNearId(curWorkShift);
            if (newId != -1) {
                WorkShift workShift2 = workShiftRepository.findById(newId).get();
                for (Worker w : workShift2.getWorkers()) {
                    w.getShifts().remove(workShift2);
                }
                workShift2.getWorkers().clear();
                for (Integer id : workerIdes) {
                    Worker worker = workerRepository.findById(id).get();
                    worker.getShifts().add(workShift2);
                    workShift2.getWorkers().add(worker);
                }
                workShiftRepository.save(workShift2);
            }
            for (Worker w : curWorkShift.getWorkers()) {
                w.getShifts().remove(curWorkShift);
            }
            curWorkShift.getWorkers().clear();
            for (Integer id : workerIdes) {
                Worker worker = workerRepository.findById(id).get();
                worker.getShifts().add(curWorkShift);
                curWorkShift.getWorkers().add(worker);
            }
            workShiftRepository.save(curWorkShift);
            return '0';
        } catch(Exception e){
            return '1';
        }
    }

    private Integer getNearId(WorkShift curWorkShift){
        if (curWorkShift.getStartTime().getHours() == 0) {
            for (WorkShift workShift : workShiftRepository.findAll()) {
                if (checkDateTimeEquals(workShift.getEndTime(), curWorkShift.getStartTime()))
                    return workShift.getId();
            }
            return -1;
        }
        else if ( curWorkShift.getEndTime().getHours() == 0){
            for (WorkShift workShift : workShiftRepository.findAll()) {
                if (checkDateTimeEquals(workShift.getStartTime(), curWorkShift.getEndTime()))
                    return workShift.getId();
            }
            return -1;
        }
        else{
            return curWorkShift.getId();
        }
    }



    private boolean checkDateTimeEquals(Date date1, Date date2) {
        return date1.getTime() == date2.getTime();
    }
    public void delInfo(WorkShift shift){
        for(Worker w: shift.getWorkers()){
            w.getShifts().remove(shift);
            workerRepository.save(w);
        }
        shift.getWorkers().clear();
        workShiftRepository.delete(shift);
    }


    public WorkShift getMergedShifts(Integer workShiftId){
        if (!workShiftRepository.findById(workShiftId).isPresent())
            return null;
        WorkShift curWorkShift = workShiftRepository.findById(workShiftId).get();
        if (curWorkShift.getStartTime().getHours() == 0) {
            return getSomeWorkShiftByMidTime(curWorkShift.getStartTime());
        }
        else if ( curWorkShift.getEndTime().getHours() == 0){
            return getSomeWorkShiftByMidTime(curWorkShift.getEndTime());
        }
        else{
            return curWorkShift;
        }
    }

}
