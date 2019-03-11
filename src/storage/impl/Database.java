package storage.impl;

import ru.taranov.dto.User;
import storage.Storage;

import java.sql.*;

public class Database implements Storage {

    private static final String URL = "jdbc:mysql://localhost:3306/usersdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() throws SQLException {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public boolean loginVerification(User user) {
        try (PreparedStatement prSt = getConnection().
                prepareStatement("SELECT * FROM users WHERE login = ?")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("INSERT INTO users (login, password, name, surname) VALUES (?,?,?,?)")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("SELECT * FROM users WHERE login = ? and password = ?")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("SELECT * FROM users WHERE login = ?")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("UPDATE users SET deposit = ? WHERE login = ?")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("UPDATE users SET deposit = deposit + ?  WHERE login = ?");
             PreparedStatement prepSt = getConnection().
                     prepareStatement("UPDATE users SET deposit = deposit - ?  WHERE login = ?")){

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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("UPDATE users SET password = ? WHERE login = ?")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("UPDATE users SET name = ? WHERE login = ?")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("UPDATE users SET surname = ? WHERE login = ?")){
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
        try (PreparedStatement prSt = getConnection().
                prepareStatement("DELETE FROM users WHERE login = ?")){
            prSt.setString(1, user.getLogin());
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
