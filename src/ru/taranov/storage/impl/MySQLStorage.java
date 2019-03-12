package ru.taranov.storage.impl;

import ru.taranov.dto.User;
import ru.taranov.storage.Storage;
import ru.taranov.database.DatabaseConnection;

import java.sql.*;

public class MySQLStorage implements Storage {

    private final String SELECT_USER = "SELECT * FROM users WHERE login = ?";
    private final String INSERT_NEW_USERS = "INSERT INTO users (login, password, name, surname) VALUES (?,?,?,?)";
    private final String SELECT_USERS = "SELECT * FROM users WHERE login = ? and password = ?";
    private final String UPDATE_DEPOSIT = "UPDATE users SET deposit = ? WHERE login = ?";
    private final String UPDATE_ANOTHER_DEPOSIT = "UPDATE users SET deposit = deposit + ?  WHERE login = ?";
    private final String UPDATE_YOUR_DEPOSIT = "UPDATE users SET deposit = deposit - ?  WHERE login = ?";
    private final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
    private final String UPDATE_NAME = "UPDATE users SET name = ? WHERE login = ?";
    private final String UPDATE_SURNAME = "UPDATE users SET surname = ? WHERE login = ?";
    private final String DELETE_USERS = "DELETE FROM users WHERE login = ?";

    @Override
    public boolean loginVerification(User user) {
        try ( Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
              PreparedStatement prSt = con.prepareStatement(SELECT_USER)){
            prSt.setString(1, user.getLogin());
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                if (resSet.getString("login").equals(user.getLogin())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void registration(User user) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(INSERT_NEW_USERS)){
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getName());
            prSt.setString(4, user.getSurname());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean authorization(User user) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(SELECT_USERS)){
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                if (resSet.getString("password").equals(user.getPassword())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(User entry) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(SELECT_USER)){
            prSt.setString(1, entry.getLogin());
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                if (resSet.getString("login").equals(entry.getLogin())) {
                    String name = resSet.getString(2);
                    String surname = resSet.getString(3);
                    String login = resSet.getString(4);
                    String password = resSet.getString(5);
                    double deposit = resSet.getDouble(6);
                    User user = new User(name, surname, login, password, deposit);
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void increaseTheDeposit(User user, int addition) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(UPDATE_DEPOSIT)){
            prSt.setString(2, user.getLogin());
            prSt.setDouble(1, user.getDeposit() + addition);
            user.setDeposit(user.getDeposit() + addition);
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean transferMoney(User user, User anotherEntry, double money) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(UPDATE_ANOTHER_DEPOSIT);
             PreparedStatement prepSt = con.
                     prepareStatement(UPDATE_YOUR_DEPOSIT)){

            prSt.setString(2, anotherEntry.getLogin());
            prSt.setDouble(1, money);
            prepSt.setString(2, user.getLogin());
            prepSt.setDouble(1, money);
            user.setDeposit(user.getDeposit() - money);
            prSt.executeUpdate();
            prepSt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void changeYourPassword(User user, String newPassword) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(UPDATE_PASSWORD)){
            prSt.setString(2, user.getLogin());
            prSt.setString(1, newPassword);
            user.setPassword(newPassword);
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeYourName(User user, String newName) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(UPDATE_NAME)){
            prSt.setString(2, user.getLogin());
            prSt.setString(1, newName);
            user.setName(newName);
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeYourSurname(User user, String newSurname) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(UPDATE_SURNAME)){
            prSt.setString(2, user.getLogin());
            prSt.setString(1, newSurname);
            user.setSurname(newSurname);
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(User user) {
        try (Connection con = DatabaseConnection.getInstance().getDatabaseConnection();
             PreparedStatement prSt = con.prepareStatement(DELETE_USERS)){
            prSt.setString(1, user.getLogin());
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
