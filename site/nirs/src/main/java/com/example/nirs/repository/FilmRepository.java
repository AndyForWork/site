package com.example.nirs.repository;

import com.example.nirs.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    @Query("Select count(*) from Ticket as t join Session as s on t.sessionId.id = s.id  join Film as f on s.film.id = f.id where f.id = ?1 and DATEADD(hour,3,s.startTime) >= CURRENT_TIMESTAMP")
    Integer countTicketsForFilm(Integer id);
    Optional<Film> findByName(String name);
}
