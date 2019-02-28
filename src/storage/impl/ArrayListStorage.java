package storage.impl;

import storage.Storage;
import ru.taranov.dto.User;

import java.util.ArrayList;
import java.util.List;

public class ArrayListStorage implements Storage {

    private List<User> userList = new ArrayList();

    @Override
    public boolean loginVerification(User user){
        return userList.stream().anyMatch(existUser -> existUser.getLogin().equals(user.getLogin()));
    }

    @Override
    public void registration(User user) {
        userList.add(user);
    }

    @Override
    public User authorization(User entry) {
        return userList.stream().filter(user -> user.getLogin().equals(entry.getLogin())
                && user.getPassword().equals(entry.getPassword())).findAny().orElse(null);
    }

    @Override
    public void increaseTheDeposit(User user, int addition) throws NumberFormatException {
        user.setDeposit(user.getDeposit() + addition);
    }

    @Override
    public boolean transferMoney(User user, User anotherEntry, double money) {
        for (User anotherUser : userList) {
            if (anotherUser.getLogin().equals(anotherEntry.getLogin())){
                anotherUser.setDeposit(anotherUser.getDeposit() + money);
                user.setDeposit(user.getDeposit() - money);
                return true;
            }
        }
        return false;
    }

    public void changeYourName(User user, String newName) {
        user.setName(newName);
    }

    public void changeYourSurname(User user, String newSurname) {
        user.setSurname(newSurname);
    }

    public void changeYourPassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    @Override
    public void deleteAccount(User user){
        userList.remove(user);
    }

    @Override
    public String toString() {
        return "User = " + userList;
    }
}