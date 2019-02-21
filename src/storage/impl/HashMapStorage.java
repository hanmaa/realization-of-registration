package storage.impl;

import storage.Storage;
import ru.taranov.dto.User;

import java.util.HashMap;
import java.util.Map;


public class HashMapStorage implements Storage {

    private Map<String, User> userMap = new HashMap();

    @Override
    public void registration(User user) {
        userMap.put(user.getLogin(), user);
    }

    @Override
    public boolean authorization(User user) {
        if (userMap.containsKey(user.getLogin())){
            return true;
        }
        return false;
    }

    @Override
    public void increaseTheDeposit(User user, int addition) {
        user.setDeposit(user.getDeposit() + addition);
    }

    @Override
    public void transferMoney(User user, User anotherEntry, double money) {
        if (userMap.containsKey(anotherEntry.getLogin())){
            for (User anotherUser : userMap.values()) {
                if (anotherUser.equals(anotherEntry)){
                    anotherUser.setDeposit(anotherUser.getDeposit() + money);
                    user.setDeposit(user.getDeposit() - money);
                }
            }
        }
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
    public void deleteAccount(User user) {
        userMap.remove(user);
    }

    @Override
    public String toString() {
        return "User = " + userMap;
    }
}