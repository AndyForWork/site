package com.example.nirs.controllers;


import com.example.nirs.dtos.LoginDTO;
import com.example.nirs.dtos.RegistrationDTO;
import com.example.nirs.models.User;
import com.example.nirs.services.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger("AuthenticationController");



    @Autowired
    private AuthenticationManager authenticationManager;


//
//    @PostMapping("/auth/register")
//    public User registerUser(@RequestBody RegistrationDTO body){
//        return authenticationService.registerUser(body.getUsername(), body.getPassword());
//    }
//
//    @PostMapping("/auth/login")
//    public LoginResponseDTO loginUser(@RequestBody LoginDTO body){
//        return authenticationService.loginUser(body.getUsername(), body.getPassword());
//    }
//
//    @GetMapping("/auth/authority")
//    public ResponseEntity<String> getUserAuthority(){
//        SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        String res = authenticationService.getMaxAuthority(
//                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        logger.info(res);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }


    @GetMapping("/login")
    public ModelAndView loginUserGet(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("loginPage");
        mav.addObject("loginDTO", new RegistrationDTO());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @PostMapping("/login")
    public RedirectView loginUserPost(@Valid LoginDTO body){
        logger.info("Logged in" + body.toString());
        //сделать проверки
        return new RedirectView("/");
    }

    @GetMapping("/register")
    public ModelAndView registerUserGet(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("registerPage");
        mav.addObject("registrationDTO", new RegistrationDTO());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @PostMapping("/register")
    public RedirectView registerUserPost(@Valid RegistrationDTO body){
        //сделать проверки
        authenticationService.registerUser(body.getUsername(), body.getPassword());
        logger.info("authed " + body.toString() );
        return new RedirectView("/");
    }


    @GetMapping("/data")
    public void data( @AuthenticationPrincipal User user){
        logger.info(user.toString());
    }
}
