package com.example.nirs.controllers;

import com.example.nirs.models.Session;
import com.example.nirs.models.User;
import com.example.nirs.services.SessionService;
import com.example.nirs.services.TicketService;
import com.example.nirs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @GetMapping("/ticket/delete/{ticketId}")
    public RedirectView deleteTicket(@PathVariable Integer ticketId){
        ticketService.deleteTicket(ticketId);
        return new RedirectView("/admin/ticket/all");
    }

    @GetMapping("/admin/ticket/all")
    public ModelAndView adminAllTicketGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllTickets");
        mav.addObject("tickets", ticketService.getAllTickets());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }


    @GetMapping("/ticket/buy/{sessionId}")
    public ModelAndView buyTicketGet(@PathVariable Integer sessionId, @AuthenticationPrincipal User user){
        Session session = sessionService.getById(sessionId);
        ModelAndView mav = new ModelAndView("buyTicket");
        mav.addObject("selected_session",session);
        mav.addObject("user",userService.loadUserByUsername(user.getUsername()));
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @PostMapping("/ticket/buy/{sessionId}")
    public RedirectView buyTicketPost(@PathVariable Integer sessionId,  Integer[] places, @AuthenticationPrincipal User user){
        ticketService.createTickets(sessionId,user.getUserId(),places);
        return new RedirectView("/user/get/"+user.getUserId().toString());
    }
}
