package com.example.nirs.repository;

import com.example.nirs.models.Session;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update Session as s set s.hall = (Select h from Hall as h where h.name='DEFAULT_HALL') where s.id in (Select s.id from Session as s join Hall as h on s.hall.id=h.id join Cinema as c on hall.cinema.id=c.id where c.id = ?1)")
    void setHallToDefaultInSessionInCinemasWithId(Integer cinemaId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update Session as s set s.film = (Select f from Film as f where f.name='UNKNOWN_FILM') where s.id in (Select s.id from Session as s join Film as f on s.film.id=f.id where f.id = ?1)")
    void setFilmToDefaultInSessionWithFilmId(Integer filmId);


}
