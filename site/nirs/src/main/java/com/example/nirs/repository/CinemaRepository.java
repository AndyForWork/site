package com.example.nirs.repository;

import com.example.nirs.models.Cinema;
import com.example.nirs.models.Film;
import com.example.nirs.models.WorkShift;
import com.example.nirs.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {


    @Query("Select count(*) from Ticket as t join Session as s on t.sessionId.id = s.id  join Hall as h on s.hall.id = h.id join Cinema as c on c.id = h.cinema.id where c.id = ?1 and DATEADD(hour,3,s.startTime) >= CURRENT_TIMESTAMP")
    Integer countTicketsInCinema(Integer id);

    Optional<Cinema> findByName(String name);
}