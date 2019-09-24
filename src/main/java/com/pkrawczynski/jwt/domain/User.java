package com.pkrawczynski.jwt.domain;



import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQuery(name = "User.checkLogin",
        query = "SELECT COUNT(*) from User where USERNAME = :login"
)

@Entity
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASS")
    private String pass;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, name = "ROLE")
    private Role role;


    public User(String name, String lastName, String username, String pass, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.pass = pass;
        this.role = role;
    }

    public User(String name, String lastName, String login, String pass, String role) {
        this.name = name;
        this.lastName = lastName;
        this.username = login;
        this.pass = pass;
        this.role = setRoleInConstructor(role);

    }

    public User() {}

    private Role setRoleInConstructor(String role){
        Role result = null;
        switch (role.toUpperCase()) {
            case "ADMIN":
                result = Role.ADMIN;
                break;
            case "CONSULTANT":
                result = Role.CONSULTANT;
                break;
            default:
                result = Role.CONSULTANT;
        }
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}