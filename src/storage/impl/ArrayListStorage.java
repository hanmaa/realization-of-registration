package storage.impl;

import storage.Storage;
import ru.taranov.dto.User;

import java.util.ArrayList;
import java.util.List;

public class ArrayListStorage implements Storage {

    private List<User> userList = new ArrayList();

    @Override
    public void registration(User user) {
        userList.add(user);
    }

    @Override
    public boolean authorization(User user) {
        if (userList.contains(user)){
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
        if (userList.contains(anotherEntry)){
            for (User anotherUser : userList) {
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
        userList.remove(user);
    }

    @Override
    public String toString() {
        return "User = " + userList;
    }
}
