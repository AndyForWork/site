package com.example.nirs.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JsonBackReference
    private Film film;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    @JsonBackReference
    private Hall hall;

    @NotNull
    private Integer duration;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy--MM--dd'T'HH:mm")
    private Date startTime;

    @NotNull
    private String filmType;

    @OneToMany(mappedBy = "sessionId")
    private Set<Ticket> tickets;

    private Integer costOfOneTicket;

    public Session() {
    }

    public Integer getId() {return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Session(Film film, Hall hall, Integer duration, Date startTime, String filmType, Set<Ticket> tickets) {
        this.film = film;
        this.hall = hall;
        this.duration = duration;
        this.startTime = startTime;
        this.filmType = filmType;
        this.tickets = tickets;
    }

    public Integer getCostOfOneTicket() {
        return costOfOneTicket;
    }

    public void setCostOfOneTicket(Integer costOfOneTicket) {
        this.costOfOneTicket = costOfOneTicket;
    }

    public boolean checkExistTicketByRowAndSeat(Integer row, Integer seat){
        for (Ticket ticket: tickets){
            if (ticket.getRow() == row && ticket.getSeatInRow() == seat)
                return true;
        }
        return false;
    }

    public void copyData(Session session){
        this.film=session.film;
        this.hall=session.hall;
        this.startTime=session.startTime;
        this.duration=session.duration;
        this.costOfOneTicket=session.costOfOneTicket;
        this.filmType=session.filmType;
    }
}
