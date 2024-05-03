package com.example.nirs.repository;

import com.example.nirs.models.Film;
import com.example.nirs.models.Hall;
import com.example.nirs.models.Worker;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from Hall as h where h.cinema.id = ?1")
    void deleteHallInCinemaWithId(Integer cinemaId);

    Optional<Hall> findByName(String name);
}
