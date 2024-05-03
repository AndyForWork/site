package com.example.nirs.dtos;


public class RegistrationDTO {
    private String username;
    private String password;
    private String confirmPassword;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password,  String confirmPassword){
        super();
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password;
    }
}