package storage.impl;

import storage.Storage;
import ru.taranov.dto.User;

import java.util.HashSet;
import java.util.Set;

public class HashSetStorage implements Storage {

    private Set<User> userSet = new HashSet();

    @Override
    public void registration(User user) {
        userSet.add(user);
    }

    @Override
    public boolean authorization(User user) {
        if (userSet.contains(user)){
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
        if (userSet.contains(anotherEntry)){
            for (User anotherUser : userSet) {
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
        userSet.remove(user);
    }

    @Override
    public String toString() {
        return "User = " + userSet;
    }
}
