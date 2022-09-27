package com.company.shop.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="admin")
public class Admin {


    @Id
    @Column(name="idadmin")
    //
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idAdmin;

    @Column(name = "fio")
    private String fio;

    @Column(name = "login")
    private String login;

    @Column(name = "pass")
    private String pass;

    @Column(name = "phone")
    private String phone;

    public Admin() {
    }

    public Admin(String fio, String login, String pass, String phone) {
        this.fio = fio;
        this.login = login;
        this.pass = pass;
        this.phone = phone;
    }


    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}