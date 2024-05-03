package com.example.nirs.repository;

import com.example.nirs.models.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("Select t from Ticket as t join User as u on t.buyerId.id = u.id where u.id = ?1")
    Set<Ticket> getUsersTickets(Integer userId);

}
