package com.example.nirs.utils;

import com.example.nirs.models.WorkShift;
import com.example.nirs.repository.WorkShiftRepository;
import com.example.nirs.services.WorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

@Component
public class WorkShiftScheduler {
    @Autowired
    private WorkShiftRepository workShiftRepository;

    @Autowired
    private WorkShiftService workShiftService;


    @Scheduled(cron = "0 0 0 * * 0")
    public void createNextWeekShiftSchedule(){
            for (WorkShift w: workShiftService.getAllWorkShift())
                workShiftService.delInfo(w);
        LocalDateTime date = LocalDateTime.of(LocalDate.now(),LocalTime.of(0,0,0));
        for (int i =0;i<7;i++){
            workShiftRepository.save(new WorkShift(new HashSet<>(), java.sql.Timestamp.valueOf(date.plusDays(i)),
                    java.sql.Timestamp.valueOf(date.plusDays(i).plusHours(9))));
            workShiftRepository.save(new WorkShift(new HashSet<>(), java.sql.Timestamp.valueOf(date.plusDays(i).plusHours(9)),
                    java.sql.Timestamp.valueOf(date.plusDays(i).plusHours(21))));
            workShiftRepository.save(new WorkShift(new HashSet<>(), java.sql.Timestamp.valueOf(date.plusDays(i).plusHours(21)),
                    java.sql.Timestamp.valueOf(date.plusDays(i).plusHours(24))));
        }
    }




}
