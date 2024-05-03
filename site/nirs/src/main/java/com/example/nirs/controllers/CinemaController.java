package com.example.nirs.controllers;

import com.example.nirs.models.Cinema;
import com.example.nirs.models.User;
import com.example.nirs.services.CinemaService;
import com.example.nirs.services.FilmService;
import org.hibernate.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    private Logger logger = LoggerFactory.getLogger("CinemaController");


    @Autowired
    private FilmService filmService;
//
//    @GetMapping("/cinema/all")
//    public ResponseEntity<List<Cinema>> getAllCinemas(){
//        return new ResponseEntity<>(cinemaService.getAllCinemas(), HttpStatus.OK);
//    }
//
//    @GetMapping("/cinema/get/{id}")
//    public ResponseEntity<Cinema> getCinemaById(@PathVariable Integer id){
//        Cinema returnCinema = cinemaService.getCinemaById(id);
//        if (returnCinema == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnCinema, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/cinema/del/{id}")
//    public ResponseEntity<Cinema> delCinemaById(@PathVariable Integer id){
//        return null;
//        // нужно будет изменить
//    }
//
//    //нужно будет добавить проверку для работника, чтобы один работник был только в одном кинотеатре
//    @PutMapping("/cinema/add")
//    public ResponseEntity<Cinema> addCinema(@RequestBody Cinema cinema){
//        Cinema returnCinema = cinemaService.addCinema(cinema);
//        if (returnCinema == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnCinema, HttpStatus.OK);
//    }
//
//    @PostMapping("/cinema/update/{id}")
//    public ResponseEntity<Cinema> updateCinema(@RequestBody(required = true) Cinema cinema, @PathVariable Integer id){
//        Cinema returnCinema = cinemaService.updateCinema(cinema,id);
//        if (returnCinema == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnCinema, HttpStatus.OK);
//    }
//





    @GetMapping("/cinema/get/{cinemaId}")
    public ModelAndView getCinema(@PathVariable Integer cinemaId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("getCinema");
        mav.addObject("cinema",cinemaService.getCinemaById(cinemaId));
        mav.addObject("films",filmService.getAllFilms() );
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @GetMapping("/cinema/update/{cinemaId}")
    public ModelAndView updateCinemaGet(@PathVariable Integer cinemaId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("cinemaUpdate");
        mav.addObject("cinema",cinemaService.getCinemaById(cinemaId));
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @PostMapping("/cinema/update/{cinemaId}")
    public RedirectView updateCinemaPost(@PathVariable Integer cinemaId, @AuthenticationPrincipal User user, Cinema cinema){
        logger.info(cinema.toString());
        cinemaService.updateCinema(cinema,cinemaId);
        return new RedirectView("/cinema/get/" + cinemaId.toString());
    }

    @GetMapping("/cinema/delete/{cinemaId}")
    public RedirectView deleteCinema(@PathVariable Integer cinemaId){
        cinemaService.deleteCinema(cinemaId);
        return new RedirectView("/admin/cinema/all");
    }

    @GetMapping("/cinema/add")
    public ModelAndView addCinemaGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("addCinema");
        mav.addObject("cinema", new Cinema());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }


    @PostMapping("/cinema/add")
    public RedirectView addCinemaPost(Cinema cinema){
        logger.info("ok post");
        logger.info(cinema.toString());
        cinemaService.addCinema(cinema);
        return new RedirectView("/cinema/add");
    }

    @GetMapping("/cinema/all")
    public ModelAndView AllCinemaGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("allCinemas");
        mav.addObject("cinemas", cinemaService.getAllCinemas());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }


    @GetMapping("/admin/cinema/all")
    public ModelAndView adminAllCinemaGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllCinemas");
        mav.addObject("cinemas", cinemaService.getAllCinemas());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }




}
