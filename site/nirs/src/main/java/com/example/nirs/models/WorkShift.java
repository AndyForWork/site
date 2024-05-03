package com.example.nirs.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "shifts")
    private Set<Worker> workers ;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    public Set<Worker> getWorkers() {
        return workers;
    }


    public WorkShift( Set<Worker> workers, Date startTime, Date endTime) {

        this.workers = workers;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WorkShift(Integer id, Set<Worker> workers, Date startTime, Date endTime) {
        this.id = id;
        this.workers = workers;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WorkShift() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Boolean checkContainWorkerById(Integer workerId){
        for (Worker w : workers){
            if (w.getId() == workerId)
                return true;
        }
        return false;
    }

}
