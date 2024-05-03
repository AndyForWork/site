package com.example.nirs.controllers;

import com.example.nirs.models.User;
import com.example.nirs.models.WorkShift;
import com.example.nirs.repository.CinemaRepository;
import com.example.nirs.repository.WorkShiftRepository;
import com.example.nirs.services.CinemaService;
import com.example.nirs.services.WorkShiftService;
import com.example.nirs.services.WorkerService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
public class WorkShiftController {

    @Autowired
    private WorkShiftService workShiftService;

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private WorkerService workerService;

    @GetMapping("/admin/workShift/all")
    public ModelAndView getAllWorkShifts(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllWorkShifts");
        mav.addObject("workShifts",workShiftService.getAllWorkShift());
        mav.addObject("cinemas",cinemaService.getAllCinemas());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @GetMapping("/admin/workShift/get/{workShiftId}")
    public ModelAndView getWorkShiftGet(@PathVariable  Integer workShiftId, @AuthenticationPrincipal User user){
        WorkShift workShift = workShiftService.getMergedShifts(workShiftId);
        ModelAndView mav = new ModelAndView("getWorkShift");
        mav.addObject("workShift",workShift);
        mav.addObject("cinemas",cinemaService.getAllCinemas());
        mav.addObject("workers",workerService.getAllWorkers());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @PostMapping("/admin/workShift/get/{workShiftId}")
    public RedirectView getWorkShiftPost(Integer[] workers, @PathVariable Integer workShiftId){
        workShiftService.updateWorkShift(workers,workShiftId);
        return new RedirectView("/admin/workShift/all");
    }



}
