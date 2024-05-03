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
import java.util.Set;

@Entity
@Data
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true, length = 500)
    private String name;

    @NotNull
    @Column(length = 2000)
    private String info;

    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Film> films = new HashSet<>();


    public Genre(String name, String info, Set<Film> films) {
        this.name = name;
        this.info = info;
        this.films = films;
    }

    public Genre(String name, String info) {
        this.name = name;
        this.info = info;
        films = new HashSet<>();
    }

    public void clearId(){
        this.id = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    public void copyData(Genre genre){
        this.name = genre.name;
        this.info = genre.info;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public Genre() {
    }
}
