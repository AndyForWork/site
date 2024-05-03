package com.example.nirs.services;

import com.example.nirs.models.Film;
import com.example.nirs.models.Genre;
import com.example.nirs.repository.FilmRepository;
import com.example.nirs.repository.GenreRepository;
import com.example.nirs.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public List<Film> getAllFilms(){
        List<Film> films =  filmRepository.findAll();
        return films;
    }

    public Film getById(Integer id){
        if (filmRepository.findById(id).isPresent())
            return filmRepository.findById(id).get();
        else
            return null;
    }

    /*
    0 - ok
    1 - непредвиденная ошибка;
    2 - указанного id нет в списке
    3 - есть уже купленные билеты
    */
    public char delFilmById(Integer id){
        try {
            if (!filmRepository.findById(id).isPresent())
                return '2';
            if (filmRepository.countTicketsForFilm(id) != 0)
                return '3';

            Film deleatingFilm = filmRepository.findById(id).get();
            Genre genre = deleatingFilm.getGenre();
            if (genre != null) {
                genre.getFilms().remove(deleatingFilm);
                genreRepository.save(genre);
                deleatingFilm.setGenre(null);
            }
            sessionRepository.setFilmToDefaultInSessionWithFilmId(id);
            filmRepository.delete(deleatingFilm);
            return '0';
        } catch(Exception e){
            return '1';
        }
    }

    public Film addFilm(Film film){
        if (filmRepository.findByName(film.getName()).isPresent())
            return null;
        filmRepository.save(film);
        return filmRepository.findByName(film.getName()).get();
    }

    public char updateFilm(Film film, Integer id){
        try {
            if (!filmRepository.findById(id).isPresent())
                return '2';
            Film prevFilm = filmRepository.findById(id).get();
            prevFilm.copyData(film);
            filmRepository.save(prevFilm);
            return '0';
        } catch(Exception e){
            return '1';
        }
    }

}