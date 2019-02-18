package ru.taranov.dto.impl;

import ru.taranov.dto.Storage;
import ru.taranov.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayListStorage implements Storage {

    private List<User> userList = new ArrayList();

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public void registration() {
        Scanner scan = new Scanner(System.in);
        System.out.println("enter login");
        String login = scan.nextLine();
        System.out.println("enter password");
        String password = scan.nextLine();
        User user = new User(login, password);
        System.out.println("enter your data");
        System.out.println("name ");
        user.setName(scan.nextLine());
        System.out.println("surname");
        user.setSurname(scan.nextLine());
        userList.add(user);
    }

    @Override
    public void authorization() {
        Scanner scan = new Scanner(System.in);
        System.out.println("enter login");
        String login = scan.nextLine();
        System.out.println("enter password");
        String password = scan.nextLine();
        User entry = new User(login, password);

        if (userList.contains(entry)){
            for (User user : userList) {
                if (user.equals(entry)){
                    do {
                        System.out.println("choose a number");
                        System.out.println("1)increase your deposit");
                        System.out.println("2)transfer money to another account");
                        System.out.println("3)change your data");
                        System.out.println("4)delete your account");
                        System.out.println("5)find out the amount of money on Deposit");
                        System.out.println("6)get full information about you");
                        System.out.println("7)exit");

                        int choice = Integer.parseInt(scan.nextLine());

                        switch (choice) {
                            case 1: {
                                increaseTheDeposit(user);
                                break;
                            } case 2: {
                                System.out.println("enter the login of the person you want to send money to");
                                String loginAnotherUser = scan.nextLine();
                                User anotherEntry = new User(loginAnotherUser);
                                if (userList.contains(anotherEntry)){
                                    for (User anotherUser : userList) {
                                        if (anotherUser.equals(anotherEntry)){
                                            transferMoney(user, anotherUser);
                                        }
                                    }
                                }
                                break;
                            } case 3: {
                                changeYourData(user);
                                break;
                            } case 4: {
                                deleteAccount(user);
                                break;
                            } case 5: {
                                System.out.println("On your deposit " + user.getDeposit());
                                break;
                            } case 6: {
                                System.out.println(user.getFullInformation());
                                break;
                            } case 8: {
                                System.out.println("goodbye");
                                System.exit(0);
                            }
                        }
                    }while (userList.contains(user));
                }
            }
        }
    }

    @Override
    public void increaseTheDeposit(User user) {
        System.out.println("How much money do you want to deposit?");
        Scanner scan = new Scanner(System.in);
        int addition = Integer.parseInt(scan.nextLine());
        user.setDeposit(user.getDeposit() + addition);
        System.out.println("Now on your deposit " + user.getDeposit());
    }

    @Override
    public void transferMoney(User user, User anotherUser) {
        double deposit = user.getDeposit();
        System.out.println("You have " + deposit + " in your deposit, how much you want to transfer?");
        Scanner scan = new Scanner(System.in);
        double money = Integer.parseInt(scan.nextLine());

        anotherUser.setDeposit(anotherUser.getDeposit() + money);
        user.setDeposit(user.getDeposit() - money);
        System.out.println("Now you have " + user.getDeposit());
    }

    @Override
    public void changeYourData(User user) {
        System.out.println("choose a number what you want to change");
        System.out.println("1)name");
        System.out.println("2)surname");
        System.out.println("3)password");

        Scanner scan = new Scanner(System.in);
        int choice = Integer.parseInt(scan.nextLine());

        switch (choice) {
            case 1: {
                System.out.println("enter new name ");
                String newName = scan.nextLine();
                user.setName(newName);
                break;
            } case 2: {
                System.out.println("enter new surname ");
                String newSurname = scan.nextLine();
                user.setSurname(newSurname);
                break;
            } case 3: {
                System.out.println("enter new password ");
                String newPassword = scan.nextLine();
                user.setPassword(newPassword);
                break;
            }
        }
    }

    @Override
    public void deleteAccount(User user) {
        System.out.println("Do you really want to delete your account?");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        if (answer.equals("yes")) {
            userList.remove(user);
        }
    }

    @Override
    public String toString() {
        return "ru.taranov.dto.User=" + userList;
    }
}
