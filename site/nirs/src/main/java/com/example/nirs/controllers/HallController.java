package com.example.nirs.controllers;

import com.example.nirs.models.Cinema;
import com.example.nirs.models.Hall;
import com.example.nirs.models.User;
import com.example.nirs.services.FilmService;
import com.example.nirs.services.HallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class HallController {

    @Autowired
    private HallService hallService;


    private Logger logger = LoggerFactory.getLogger("HallController");
    @Autowired
    private FilmService filmService;
//
//    @GetMapping("/hall/all")
//    public ResponseEntity<List<Hall>> getAllHalls(){
//        return new ResponseEntity<>(hallService.getAllHalls(), HttpStatus.OK);
//    }
//
//    @GetMapping("/hall/get/{hallId}")
//    public ResponseEntity<Hall> getHallById(@PathVariable Integer hallId){
//        Hall returnHall = hallService.getHallById(hallId);
//        if (returnHall == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnHall, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/hall/del/{hallId}")
//    public ResponseEntity<Boolean> delHallById(@PathVariable Integer hallId){
//        Boolean res = hallService.delHall(hallId);
//        if (res)
//            return new ResponseEntity<>(true, HttpStatus.OK);
//        else
//            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//    }
//
//    @PutMapping("/hall/add/{cinemaId}")
//    public ResponseEntity<Hall> addNewHall(@RequestBody(required = true) Hall newHall,@PathVariable Integer cinemaId){
//        Hall savedHall = hallService.addHall(newHall,cinemaId);
//        if (savedHall == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(savedHall, HttpStatus.OK);
//    }
//
//    @PostMapping("/hall/update/{hallId}")
//    public ResponseEntity<Hall> updateHallInfo(@RequestBody(required = true) Hall hall, @PathVariable Integer hallId){
//        Hall updatedHall = hallService.updateHall(hall,hallId);
//        if (updatedHall == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(updatedHall, HttpStatus.OK);
//    }
    @GetMapping("/hall/add/{cinemaId}")
    public ModelAndView addHallGet(@PathVariable Integer cinemaId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("addHall");
        //   mav.addObject("cinema", cinemaRepository.findById(cinemaId).get());
        mav.addObject("hall",new Hall());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @PostMapping("/hall/add/{cinemaId}")
    public RedirectView addHallPost( Hall hall, @PathVariable Integer cinemaId){
        logger.info("ok post");
        logger.info(String.valueOf(cinemaId));
        logger.info(hall.toString());
        hallService.addHall(hall,cinemaId);
        return new RedirectView("/hall/add/"+cinemaId.toString());
    }
    @GetMapping("/hall/delete/{hallId}")
    public RedirectView deleteHall(@PathVariable Integer hallId){
        hallService.delHall(hallId);
        return new RedirectView("/admin/hall/all");
    }
    @GetMapping("/hall/get/{hallId}")
    public ModelAndView getHall(@PathVariable Integer hallId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("getHall");
        mav.addObject("hall",hallService.getHallById(hallId));
        mav.addObject("films",filmService.getAllFilms() );
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @GetMapping("/admin/hall/all")
    public ModelAndView adminAllHallGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllHalls");
        mav.addObject("halls",hallService.getAllHalls());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @GetMapping("/hall/update/{hallId}")
    public ModelAndView hallUpdateGet(@PathVariable Integer hallId, @AuthenticationPrincipal User user)
    {
        ModelAndView mav = new ModelAndView("hallUpdate");
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        mav.addObject("hall",hallService.getHallById(hallId));
        return mav;
    }
    @PostMapping("/hall/update/{hallId}")
    public RedirectView hallUpdateGet(@PathVariable Integer hallId, @AuthenticationPrincipal User user, Hall hall)
    {
        hallService.updateHall(hall,hallId);
        return new RedirectView("/hall/get/" + hallId.toString());
    }

}
