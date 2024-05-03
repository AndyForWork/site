package com.example.nirs.controllers;

import com.example.nirs.models.User;
import com.example.nirs.models.Worker;
import com.example.nirs.services.TicketService;
import com.example.nirs.services.UserService;
import com.example.nirs.services.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/admin/users/all")
    public ModelAndView adminAllUserGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllUsers");
        mav.addObject("users", userService.getAllUsers());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @GetMapping("/user/get/{userId}")
    public ModelAndView getUser(@PathVariable Integer userId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("userInfo");
        mav.addObject("user",userService.getById(userId));
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        mav.addObject("worker",workerService.getWorkerById(userId));
        mav.addObject("tickets",ticketService.getUsersTickets(userId));
        return  mav;
    }
    @PostMapping("/user/get/{userId}")
    public RedirectView addBalance(@PathVariable Integer userId, Integer addBalance){
        userService.addBalance(userId, addBalance);
        return  new RedirectView("/user/get/" +  userId.toString());
    }


}
