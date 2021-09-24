package com.epam.rd.java.basic.finalProject.entity;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String encrypt;
    private Role role;
    private UserStatus statusName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatusName() {
        return statusName;
    }

    public void setStatusName(UserStatus statusName) {
        this.statusName = statusName;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id
                && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(encrypt, user.encrypt)
                && role == user.role
                && statusName == user.statusName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, encrypt, role, statusName);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", statusName=" + statusName +
                '}';
    }
}


