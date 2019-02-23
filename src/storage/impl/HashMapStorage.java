package storage.impl;

import storage.Storage;
import ru.taranov.dto.User;

import java.util.HashMap;
import java.util.Map;


public class HashMapStorage implements Storage {

    private Map<String, User> userMap = new HashMap();

    @Override
    public boolean loginVerification(User user){
        for (User existUser : userMap.values()) {
            if (existUser.getLogin().equals(user.getLogin())){
                return false;
            }
        }
        return true;
    }

    @Override
    public void registration(User user) {

        userMap.put(user.getLogin(), user);
    }

    @Override
    public User authorization(User entry) {
        for (User user : userMap.values()) {
            if (user.getLogin().equals(entry.getLogin()) && user.getPassword().equals(entry.getPassword())){
                return user;
            }
        }
        return null;
    }

    @Override
    public void increaseTheDeposit(User user, int addition) throws NumberFormatException {
        user.setDeposit(user.getDeposit() + addition);
    }

    @Override
    public boolean transferMoney(User user, User anotherEntry, double money) {
        for (User anotherUser : userMap.values()) {
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
        userMap.remove(user.getLogin());
    }

    @Override
    public String toString() {
        return "User = " + userMap;
    }
}
