package storage.impl;

import storage.Storage;
import ru.taranov.dto.User;

import java.util.HashSet;
import java.util.Set;

public class HashSetStorage implements Storage {

    private Set<User> userSet = new HashSet();

    @Override
    public boolean loginVerification(User user){
        return userSet.stream().anyMatch(existUser -> existUser.getLogin().equals(user.getLogin()));
    }

    @Override
    public void registration(User user) {
        userSet.add(user);
    }

    @Override
    public boolean authorization(User entry) {
        return userSet.stream().anyMatch(user -> user.getLogin().equals(entry.getLogin())
                && user.getPassword().equals(entry.getPassword()));
    }

    public User getUser(User entry) {
        return userSet.stream().filter(user -> user.getLogin().equals(entry.getLogin())
                && user.getPassword().equals(entry.getPassword())).findAny().orElse(null);
    }

    @Override
    public void increaseTheDeposit(User user, int addition) throws NumberFormatException {
        user.setDeposit(user.getDeposit() + addition);
    }

    @Override
    public boolean transferMoney(User user, User anotherEntry, double money) {
        for (User anotherUser : userSet) {
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
        userSet.remove(user);
    }

    @Override
    public String toString() {
        return "User = " + userSet;
    }
}
