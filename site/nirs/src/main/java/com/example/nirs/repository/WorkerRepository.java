package com.example.nirs.repository;

import com.example.nirs.models.WorkShift;
import com.example.nirs.models.Worker;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    @Override
    @Query("Select w from Worker w where w.id = ?1 ")
    Optional<Worker> findById(Integer id);

    @Query("Select p from Worker p where ?1 member of p.shifts")
    Set<Worker> findAllWorkersWithWorkShift(WorkShift workShift);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update Worker as w set w.workPlace = (Select c from Cinema as c where c.name='DEFAULT_CINEMA') where w.id in (Select w.id from Worker as w join Cinema as c on w.workPlace.id=c.id where c.id = ?1)")
    void setDefaultCinemaToWorkersFromCinemaWithId(Integer cinemaId);

    Optional<Worker> findByFirstName(String firstName);
}
