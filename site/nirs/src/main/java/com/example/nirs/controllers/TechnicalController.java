package com.example.nirs.controllers;

import com.example.nirs.models.Session;
import com.example.nirs.models.User;
import com.example.nirs.models.WorkShift;
import com.example.nirs.models.Worker;
import com.example.nirs.repository.*;
import com.example.nirs.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@CrossOrigin("*")
public class TechnicalController {

    Logger logger = LoggerFactory.getLogger("TestController");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private HallService hallService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/admin/menu")
    public ModelAndView adminMenu(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminMenu");
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @GetMapping("/")
    public ModelAndView mainPage(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("mainPage");
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @GetMapping("/error")
    public RedirectView errorProcess(@AuthenticationPrincipal User user){
        return new RedirectView("/");
    }
}