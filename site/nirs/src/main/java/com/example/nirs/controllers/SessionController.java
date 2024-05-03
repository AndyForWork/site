package com.example.nirs.controllers;

import com.example.nirs.models.Session;
import com.example.nirs.models.User;
import com.example.nirs.services.CinemaService;
import com.example.nirs.services.FilmService;
import com.example.nirs.services.SessionService;
import org.apache.coyote.Response;
import org.hibernate.grammars.hql.HqlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private FilmService filmService;
//
//    @GetMapping("/session/all")
//    public ResponseEntity<List<Session>> getAllSession(){
//        return new ResponseEntity<>(sessionService.getAllSession(), HttpStatus.OK);
//    }
//
//    @GetMapping("/session/get/{sessionId}")
//    public ResponseEntity<Session> getSessionById(@PathVariable Integer sessionId){
//        Session returnSession = sessionService.getById(sessionId);
//        if (returnSession == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnSession, HttpStatus.OK);
//
//    }
//
//    @PutMapping("/session/add/{hallId}/{filmId}")
//    public ResponseEntity<Session> addSession(@RequestBody(required = true) Session newSession, @PathVariable Integer hallId, @PathVariable Integer filmId){
//        Session returnSession = sessionService.addNewSession(newSession, hallId, filmId);
//        if (returnSession == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnSession, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/session/del/{sessionId}")
//    public ResponseEntity<Boolean> delSession(@PathVariable Integer sessionId){
//        Boolean res = sessionService.delSession(sessionId);
//        if (res)
//            return new ResponseEntity<>(true, HttpStatus.OK);
//        else
//            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//    }
//
//    @PostMapping("/session/update/{sessionId}")
//    public ResponseEntity<Session> updateSession(@PathVariable Integer sessionId, @RequestBody(required = true) Session newSession){
//        return null;
//    }
    @GetMapping("/session/delete/{sessionId}")
    public RedirectView deleteSession(@PathVariable Integer sessionId){
        sessionService.delSession(sessionId);
        return new RedirectView("/admin/session/all");
    }
    @GetMapping("/session/add")
    public ModelAndView addSessionGet(@AuthenticationPrincipal User user){
        ModelAndView mav= new ModelAndView("addSession");
        mav.addObject("cinemas", cinemaService.getAllCinemas());
        mav.addObject("films", filmService.getAllFilms());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @PostMapping("/session/add")
    public RedirectView addSessionPost(Session session){
        sessionService.addNewSession(session);
        return new RedirectView("/session/add");
    }

    @GetMapping("/admin/session/all")
    public ModelAndView adminAllSessionGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllSessions");
        mav.addObject("sessions",sessionService.getAllSession());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @GetMapping("/session/update/{sessionId}")
    public ModelAndView sessionUpdateGet(@PathVariable Integer sessionId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("sessionUpdate");
        mav.addObject("get_session",sessionService.getById(sessionId));

        mav.addObject("cinemas", cinemaService.getAllCinemas());
        mav.addObject("films", filmService.getAllFilms());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @PostMapping("/session/update/{sessionId}")
    public RedirectView sessionUpdatePost(@PathVariable Integer sessionId, @AuthenticationPrincipal User user, Session session){
        sessionService.updateSession(session, sessionId);
        return new RedirectView("/admin/session/all");
    }

}
