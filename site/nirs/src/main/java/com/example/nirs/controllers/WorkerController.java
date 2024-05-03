package com.example.nirs.controllers;

import com.example.nirs.models.User;
import com.example.nirs.models.Worker;
import com.example.nirs.services.CinemaService;
import com.example.nirs.services.UserService;
import com.example.nirs.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private UserService userService;

    @Autowired
    private CinemaService cinemaService;
//
//    @GetMapping("/worker/all")
//    public ResponseEntity<List<Worker>> getAllWorkers(){
//        return new ResponseEntity<>(workerService.getAllWorkers(), HttpStatus.OK);
//    }
//
//    @GetMapping("/worker/get/{id}")
//    public ResponseEntity<Worker> getWorker(@PathVariable Integer id){
//        Worker returnWorker = workerService.getWorkerById(id);
//        if (returnWorker == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnWorker, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/worker/del/{id}")
//    public ResponseEntity<Boolean> delWorker(@PathVariable Integer id){
//        Boolean res = workerService.delWorker(id);
//        if (!res )
//            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(true, HttpStatus.OK);
//    }
//
//    @PostMapping("/worker/add/{userId}")
//    public ResponseEntity<Worker> addWorker(@RequestBody Worker worker, @PathVariable Integer userId){
//        Worker returnWorker = workerService.createWorkerFromUser(worker,userId);
//        if (returnWorker == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnWorker, HttpStatus.OK);
//    }
//
//    @PutMapping("/worker/update")
//    public ResponseEntity<Worker> updateWorker(Worker worker, @RequestBody(required = true) Integer id){
//        Worker returnWorker = workerService.updateWorkerInfo(worker,id);
//        if (returnWorker == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnWorker, HttpStatus.OK);
//    }
    @GetMapping("/worker/add")
    public ModelAndView addWorkerGet(@AuthenticationPrincipal User user){
        ModelAndView mav= new ModelAndView("addWorker");
        List<User> usersNotWorkers = new ArrayList<>();
        for (User u: userService.getAllUsers()){
            if (workerService.getWorkerById(u.getUserId())==null)
                usersNotWorkers.add(u);
        }
        mav.addObject("cinemas", cinemaService.getAllCinemas());
        mav.addObject("users",usersNotWorkers);
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());

        return mav;
    }
    @PostMapping("/worker/add")
    public RedirectView addWorkerPost(Worker worker){
        workerService.createWorkerFromUser(worker);
        return new RedirectView("/worker/add");
    }




    @GetMapping("/worker/delete/{workerId}")
    public RedirectView deleteWorker(@PathVariable Integer workerId){
        workerService.delWorker(workerId);
        return new RedirectView("/admin/worker/all");
    }



    @GetMapping("/admin/worker/all")
    public ModelAndView adminAllWorkerGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllWorkers");
        mav.addObject("workers", workerService.getAllWorkers());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
}
