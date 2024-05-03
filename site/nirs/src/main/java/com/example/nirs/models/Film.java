package com.example.nirs.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true,length = 500)
    private String name;

    @NotNull
    @Column(length = 2000)
    private String shortInfo;

    @NotNull
    @Column(length = 10000)
    private String fullInfo;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date releaseDate;


    @ManyToOne
    @JoinColumn(name = "genre_id")
    @JsonBackReference
    private Genre genre;

    @Column(length = 10000)
    private Set<String> images;

    @Column(length = 500)
    private String videoTrailer;





    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Film() {
    }

    public Film(String name, String shortInfo, String fullInfo, Date releaseDate, Genre genre, Set<String> images, String videoTrailer) {
        this.name = name;
        this.shortInfo = shortInfo;
        this.fullInfo = fullInfo;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.images = images;
        this.videoTrailer = videoTrailer;
    }

    public void copyData(Film film){
        this.genre = film.genre;
        this.fullInfo = film.fullInfo;
        this.shortInfo = film.shortInfo;
        this.images = film.images;
        this.name = film.name;
        this.releaseDate = film.releaseDate;
        this.videoTrailer = film.videoTrailer;
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

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(String fullInfo) {
        this.fullInfo = fullInfo;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public String getVideoTrailer() {
        return videoTrailer;
    }

    public void setVideoTrailer(String videoTrailer) {
        this.videoTrailer = videoTrailer;
    }


    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortInfo='" + shortInfo + '\'' +
                ", fullInfo='" + fullInfo + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre=" + genre +
                ", images=" + images +
                ", videoTrailer='" + videoTrailer + '\'' +
                '}';
    }


}