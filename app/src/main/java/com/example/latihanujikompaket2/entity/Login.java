package com.example.latihanujikompaket2.entity;

public class Login {
    String idUser, status;

    public Login(String idUser, String status) {
        this.idUser = idUser;
        this.status = status;
    }

    public Login() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
