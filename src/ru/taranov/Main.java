package ru.taranov;

import ru.taranov.exceptions.*;
import ru.taranov.dto.User;
import ru.taranov.storage.Storage;
import ru.taranov.storage.impl.*;

import static ru.taranov.constants.Constants.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Storage storage = new MySQLStorage();
        mainMenuChoice(storage);
    }

    private static void mainMenuChoice(Storage storage) {
        System.out.print(HELLO);
        while (true) {
            try {
                System.out.println(MAIN_MENU);

                Scanner scan = new Scanner(System.in);
                int choice = Integer.parseInt(scan.nextLine());
                switch (choice) {
                    case 1: {
                        User user = inputData();
                        System.out.println(NAME);
                        user.setName(scan.nextLine());
                        System.out.println(SURNAME);
                        user.setSurname(scan.nextLine());
                        if (!storage.loginVerification(user)) {
                            storage.registration(user);
                        } else {
                            System.out.println(REGISTRATION_ERROR);
                        }
                        break;
                    } case 2: {
                        User entry = inputData();
                        if (storage.authorization(entry)) {
                            sideMenuChoice(storage.getUser(entry), storage);
                        } else {
                            throw new AuthorizationException();
                        }
                        break;
                    } case 3: {
                        System.out.println(GOODBYE);
                        System.exit(0);
                    }default: {
                        throw new ChoiceException();
                    }
                }
            }catch (ChoiceException | NumberFormatException e){
                System.out.println(CHOICE_EXCEPTION);
            } catch (AuthorizationException e){
                System.out.println(AUTHORIZATION_EXCEPTION);
            }
        }
    }

    private static void sideMenuChoice(User user, Storage storage) {
        while (true){
            try {
                System.out.println(SIDE_MENU);
                Scanner scan = new Scanner(System.in);
                int sideChoice = Integer.parseInt(scan.nextLine());
                switch (sideChoice) {
                    case 1: {
                        System.out.println(INCREASE_DEPOSIT);
                        int addition = Integer.parseInt(scan.nextLine());
                        storage.increaseTheDeposit(user, addition);
                        System.out.println(YOUR_DEPOSIT + user.getDeposit());
                        break;
                    } case 2: {
                        double deposit = user.getDeposit();
                        System.out.println("You have " + deposit + " in your deposit, how much you want to transfer?");
                        double money = Integer.parseInt(scan.nextLine());
                        if (money > deposit){
                            System.out.println(ERROR_TRANSFER);
                        } else {
                            System.out.println(ENTER_LOGIN);
                            String loginAnotherUser = scan.nextLine();
                            User anotherEntry = new User(loginAnotherUser);
                            if (storage.transferMoney(user, anotherEntry, money)){
                                System.out.println(TRANSFER);
                            } else {
                                throw new UserNotFoundException();
                            }
                        }
                        break;
                    } case 3: {
                        System.out.println(CHANGE_MENU);
                        changeData(user, storage);
                        break;
                    } case 4: {
                        System.out.println(DELETE_ACCOUNT);
                        String answer = scan.nextLine();
                        if (answer.equals(YES)) {
                            storage.deleteAccount(user);
                            mainMenuChoice(storage);
                        } else if (answer.equals(NO)) {
                            break;
                        } else {
                            throw new ChoiceException();
                        }
                        break;
                    } case 5: {
                        System.out.println(GOODBYE);
                        System.exit(0);
                    }default: {
                        throw new ChoiceException();
                    }
                }
            } catch (ChoiceException | NumberFormatException e) {
                System.out.println(CHOICE_EXCEPTION);
            } catch (UserNotFoundException e){
                System.out.println(USER_NOT_FOUND_EXCEPTION);
            }
        }
    }

    private static void changeData(User user, Storage storage) {
        try {
            Scanner scan = new Scanner(System.in);
            int choiceChange = Integer.parseInt(scan.nextLine());
            switch (choiceChange) {
                case 1: {
                    System.out.println(NEW_NAME);
                    System.out.println(user.toString());
                    String newName = scan.nextLine();
                    storage.changeYourName(user, newName);
                    System.out.println(user.toString());
                    break;
                } case 2: {
                    System.out.println(NEW_SURNAME);
                    System.out.println(user.toString());
                    String newSurname = scan.nextLine();
                    storage.changeYourSurname(user, newSurname);
                    System.out.println(user.toString());
                    break;
                } case 3: {
                    System.out.println(NEW_PASSWORD);
                    System.out.println(user.toString());
                    String newPassword = scan.nextLine();
                    storage.changeYourPassword(user, newPassword);
                    System.out.println(user.toString());
                    break;
                }default: {
                    throw new ChoiceException();
                }
            }
        } catch (ChoiceException e) {
            System.out.println(CHOICE_EXCEPTION);
        }
    }

    private static User inputData(){
        Scanner scan = new Scanner(System.in);
        System.out.println(LOGIN);
        String login = scan.nextLine();
        System.out.println(PASSWORD);
        String password = scan.nextLine();
        return new User(login, password);
    }
}