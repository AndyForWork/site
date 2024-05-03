package com.example.nirs.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Worker {
    @Id
    private Integer id;
    @NotNull
    private String firstName;
    @NotNull
    private String secondName;
    private String surname;
    @NotNull
    private String phone;
    @NotNull
    private String gender;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
        private Date dateOfBirth;
    private Integer salary=0;
    @NotNull
    private String position;        //эт тип должность
    @NotNull
    private String email;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema workPlace;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "working_shifts",
            joinColumns = {@JoinColumn(name = "worker_id")},
            inverseJoinColumns =  {@JoinColumn(name = "shift_id")}
    )
    private Set<WorkShift> shifts;

    public void copyInfo(Worker worker){
        this.dateOfBirth = worker.dateOfBirth;
        this.email = worker.email;
        this.firstName = worker.firstName;
        this.secondName = worker.secondName;
        this.surname = worker.surname;
        this.salary = worker.salary;
        this.gender = worker.gender;
        this.phone = worker.phone;
        this.position = worker.position;
        this.workPlace = worker.workPlace;
        this.shifts = worker.shifts;
    }

    public void copyIdFromUser(Integer id){
        this.id = id;
    }


    public Set<WorkShift> getShifts() {
        return shifts;
    }

    public void setShifts(Set<WorkShift> shifts) {
        this.shifts = shifts;
    }



    public Worker(Integer id, String firstName, String secondName, String surname, String phone, String gender, Date dateOfBirth, Integer salary, String position, String email, Cinema workPlace) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surname = surname;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.position = position;
        this.email = email;
        this.workPlace = workPlace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Worker() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cinema getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(Cinema workPlace) {
        this.workPlace = workPlace;
    }
}
