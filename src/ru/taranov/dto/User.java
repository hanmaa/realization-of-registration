package ru.taranov.dto;

import java.util.Objects;

public class User {

    private String name;
    private String surname;
    private String login;
    private String password;
    private double deposit;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String name, String surname, String login, String password, double deposit) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.deposit = deposit;
    }

    public User(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.deposit, deposit) == 0 &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, login, password, deposit);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", deposit=" + deposit +
                '}';
    }
}