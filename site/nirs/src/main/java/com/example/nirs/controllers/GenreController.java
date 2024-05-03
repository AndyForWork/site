package com.example.nirs.controllers;

import com.example.nirs.models.Genre;
import com.example.nirs.models.User;
import com.example.nirs.services.GenreService;
import lombok.Data;
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
public class GenreController {

    @Autowired
    private GenreService genreService;


    private Logger logger = LoggerFactory.getLogger("GenreController ");
//
//    @GetMapping("/genre/all")
//    public ResponseEntity<List<Genre>> getAllGenres(){
//        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
//    }
//
//    @GetMapping("/genre/get/{id}")
//    public ResponseEntity<Genre> getGenreById(@PathVariable Long id){
//        return new ResponseEntity<>(genreService.getGenreById(id), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/genre/del/{id}")
//    public ResponseEntity<Boolean> delGenre(@PathVariable Long id){
//        Boolean result = genreService.deleteGenre(id);
//        if (!result)//Я тут подумал - нужно возвращать число чтобы если что мог отследить неправильный id или отсутствие базового класса
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/genre/add")
//    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre){
//        Genre returnGenre = genreService.addGenre(genre);
//        if (returnGenre == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnGenre, HttpStatus.OK);
//    }
//
//    @PutMapping("/genre/update/{id}")
//    public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre, @PathVariable Long id){
//        Genre returnGenre = genreService.updateGenre(genre,id);
//        if (returnGenre == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnGenre, HttpStatus.OK);
//    }
    @GetMapping("/genre/delete/{genreId}")
    public RedirectView deleteGenre(@PathVariable Integer genreId, @AuthenticationPrincipal User user){
        genreService.deleteGenre(genreId);
        return new RedirectView("/admin/genre/all");
    }

    @GetMapping("/genre/add")
    public ModelAndView addGenreGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("addGenre");
        mav.addObject("genre", new Genre());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }


    @PostMapping("/genre/add")
    public RedirectView addGenrePost(Genre genre){
        logger.info("ok post");
        logger.info(genre.toString());
        genreService.addGenre(genre);
        return new RedirectView("/genre/add");
    }

    @GetMapping("/admin/genre/all")
    public ModelAndView adminAllGenreGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllGenres");
        mav.addObject("genres",genreService.getAllGenres());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }

    @GetMapping("/genre/update/{genreId}")
    public ModelAndView updateGenreGet(@PathVariable Integer genreId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("genreUpdate");
        mav.addObject("genre", genreService.getGenreById(genreId));
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());
        return mav;
    }
    @PostMapping("/genre/update/{genreId}")
    public RedirectView updateGenrePost(@PathVariable Integer genreId, @AuthenticationPrincipal User user, Genre genre){
        genreService.updateGenre(genre, genreId);
        return new RedirectView("/admin/genre/all");
    }

}
