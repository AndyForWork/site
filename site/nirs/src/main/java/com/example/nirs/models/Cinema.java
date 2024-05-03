package com.example.nirs.models;

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
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true, length = 500)
    private String name;

    @NotNull
    @Column(length = 2000)
    private String address;

    @OneToMany(mappedBy = "cinema")
    @JsonManagedReference
    private Set<Hall> halls = new HashSet<>();

    @OneToMany(mappedBy = "workPlace")
    private Set<Worker> workers = new HashSet<>();


    @Column(length = 10000)
    private String[] images;

    public Cinema(String name, String address, Set<Hall> halls, Set<Worker> workers) {
        this.name = name;
        this.address = address;
        this.halls = halls;
//        this.workers = workers;
    }

    public void copyData(Cinema cinema){
        this.address = cinema.address;
        this.name = cinema.name;
        this.images = cinema.images;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Hall> getHalls() {
        return halls;
    }

    public void setHalls(Set<Hall> halls) {
        this.halls = halls;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    public Cinema() {
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Cinema(Integer id, String name, String address, Set<Hall> halls, Set<Worker> workers, String[] images) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.halls = halls;
        this.workers = workers;
        this.images = images;
    }

    public Cinema(String name, String address, Set<Hall> halls, Set<Worker> workers, String[] images) {
        this.name = name;
        this.address = address;
        this.halls = halls;
        this.workers = workers;
        this.images = images;
    }
}
