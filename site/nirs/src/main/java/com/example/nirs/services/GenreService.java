package com.example.nirs.services;

import com.example.nirs.models.Film;
import com.example.nirs.models.Genre;
import com.example.nirs.repository.FilmRepository;
import com.example.nirs.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FilmRepository filmRepository;

    public List<Genre> getAllGenres(){
        List<Genre> genres =  genreRepository.findAll();
        return genres;
    }

    public Genre getGenreById(Integer id){
        return genreRepository.findById(id).get();
    }

    public Genre addGenre(Genre genre){
        if (genreRepository.findByName(genre.getName()).isPresent())
            return null;
        else {
            genre.clearId();
            genreRepository.save(genre);
            return genre;
        }
    }

    public char updateGenre(Genre genre, Integer id){
        try{
        if (!genreRepository.findById(id).isPresent())
            return '2';
        Genre prevGenre = genreRepository.findById(id).get();
        prevGenre.copyData(genre);
        genreRepository.save(prevGenre);
        return '0';
        } catch(Exception e){
            return '1';
        }
    }


    /*
    0 - ok
    1 - непредвиденная ошибка;
    2 - указанного id нет в списке
    3 - нет базового жанра
    */
    public char deleteGenre(Integer id){
        try {
            if (!genreRepository.findById(id).isPresent())
                return '2';
            if (!genreRepository.findByName("UNKNOWN_GENRE").isPresent())
                return '3';
            Genre genre = genreRepository.findById(id).get();
            Genre basicGenre = genreRepository.findByName("UNKNOWN_GENRE").get();
            for (Film film : genre.getFilms()) {
                film.setGenre(basicGenre);
                filmRepository.save(film);
            }
            genreRepository.delete(genre);
            return '0';
        }catch(Exception e){
            return '1';
        }
    }



}
