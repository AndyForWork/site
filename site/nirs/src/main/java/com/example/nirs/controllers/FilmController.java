package com.example.nirs.controllers;

import com.example.nirs.models.Film;
import com.example.nirs.models.Genre;
import com.example.nirs.models.User;
import com.example.nirs.services.CinemaService;
import com.example.nirs.services.FilmService;
import com.example.nirs.services.GenreService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class FilmController {

    @Autowired
    private FilmService filmService;


    private Logger logger = LoggerFactory.getLogger("CinemaController");

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private GenreService genreService;
//
//    @GetMapping("/film/all")
//    public ResponseEntity<List<Film>> getAllFilms(){
//        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
//    }
//
//    @GetMapping("/film/get/{id}")
//    public ResponseEntity<Film> getFilmById(@PathVariable Integer id){
//        Film returnFilm = filmService.getById(id);
//        if (returnFilm == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnFilm, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/film/del/{id}")
//    public ResponseEntity<Boolean> delFilmById(@PathVariable Integer id){
//        if (filmService.delFilmById(id))
//            return new ResponseEntity<>(true, HttpStatus.OK);
//        else
//            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//    }
//
//    @PutMapping("/film/add")
//    public ResponseEntity<Film> addFilm(@RequestBody(required = true) Film film){
//        Film returnFilm = filmService.addFilm(film);
//        if (returnFilm == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(returnFilm, HttpStatus.OK);
//    }
//
//    @PostMapping("/film/update/{id}")
//    public ResponseEntity<Film> updateFilm(@RequestBody(required = true) Film film, @PathVariable Integer id){
//        Film retunFilm = filmService.updateFilm(film,id);
//        if (retunFilm == null)
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>(retunFilm, HttpStatus.OK);
//    }
    @GetMapping("/film/get/{filmId}")
    public ModelAndView getFilm(@PathVariable Integer filmId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("getFilm");
        mav.addObject("film",filmService.getById(filmId));
        mav.addObject("cinemas",cinemaService.getAllCinemas());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());

        return mav;
    }
    @GetMapping("/film/update/{filmId}")
    public ModelAndView updateFilmGet(@PathVariable Integer filmId, @AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("filmUpdate");
        mav.addObject("film",filmService.getById(filmId));
        mav.addObject("genres", genreService.getAllGenres());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());

        return mav;
    }

    @PostMapping("/film/update/{filmId}")
    public RedirectView updateFilmPost(@PathVariable Integer filmId, @AuthenticationPrincipal User user, Film film){
        filmService.updateFilm(film, filmId);
        return new RedirectView("/film/get/" + filmId.toString());
    }

    @GetMapping("/film/add")
    public ModelAndView addFilmGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("addFilm");
        mav.addObject("genres", genreService.getAllGenres());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());

        return mav;
    }


    @PostMapping("/film/add")
    public RedirectView addFilmPost(Film film){
        filmService.addFilm(film);
        return new RedirectView("/film/all");
    }

    @GetMapping("/film/all")
    public ModelAndView allFilmGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("allFilms");
        mav.addObject("films",filmService.getAllFilms());
        mav.addObject("genres",genreService.getAllGenres());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());

        return mav;
    }
    @GetMapping("/admin/film/all")
    public ModelAndView adminAllFilmGet(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView("adminAllFilms");
        mav.addObject("films",filmService.getAllFilms());
        if (user!=null)
            mav.addObject("currentUserId", user.getUserId());

        return mav;
    }

    @GetMapping("/film/delete/{filmId}")
    public RedirectView deleteFilm(@PathVariable Integer filmId){
        filmService.delFilmById(filmId);
        return new RedirectView("/admin/film/all");
    }

}
