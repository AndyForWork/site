package com.example.nirs.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer row;

    @NotNull
    private Integer seatInRow;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyerId;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session sessionId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getSeatInRow() {
        return seatInRow;
    }

    public void setSeatInRow(Integer seatInRow) {
        this.seatInRow = seatInRow;
    }

    public User getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(User buyerId) {
        this.buyerId = buyerId;
    }


    public Session getSessionId() {
        return sessionId;
    }

    public void setSessionId(Session sessionId) {
        this.sessionId = sessionId;
    }

    public Ticket(Integer row, Integer seatInRow, User buyerId, Session sessionId) {
        this.row = row;
        this.seatInRow = seatInRow;
        this.buyerId = buyerId;
        this.sessionId = sessionId;
    }

    public Ticket() {
    }
}
