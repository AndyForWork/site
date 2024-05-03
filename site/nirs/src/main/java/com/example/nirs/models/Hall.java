package com.example.nirs.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 500)
    private String name;


    @NotNull
    private Integer rows;

    @NotNull
    private Integer seatsInRow;

    @Column(length = 10000)
    private String[] images;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    @JsonBackReference
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    @JsonManagedReference
    private Set<Session> sessionSet = new HashSet<>();

    @NotNull
    @Column(length = 10000)
    private String info;

    public Set<Session> getSessionSet() {
        return sessionSet;
    }

    public void setSessionSet(Set<Session> sessionSet) {
        this.sessionSet = sessionSet;
    }

    public Hall(String name, Integer rows, Integer seatsInRow, Cinema cinema) {
        this.name = name;
        this.rows = rows;
        this.seatsInRow = seatsInRow;
        this.cinema = cinema;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getSeatsInRow() {
        return seatsInRow;
    }

    public void setSeatsInRow(Integer seatsInRow) {
        this.seatsInRow = seatsInRow;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void copyData(Hall hall){
        this.name = hall.name;
        this.rows = hall.rows;
        this.seatsInRow = hall.seatsInRow;
        this.info = hall.info;
        this.images = hall.images;
    }

    public Hall() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return Objects.equals(id, hall.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rows=" + rows +
                ", seatsInRow=" + seatsInRow +
                ", info = " +info+
                '}';
    }

    public Hall(Integer id, String name, Integer rows, Integer seatsInRow, String[] images, Cinema cinema, Set<Session> sessionSet, String info) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.seatsInRow = seatsInRow;
        this.images = images;
        this.cinema = cinema;
        this.sessionSet = sessionSet;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}