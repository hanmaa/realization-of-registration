import ru.taranov.dto.User;
import storage.impl.ArrayListStorage;
import storage.impl.HashSetStorage;
import storage.Storage;
import static constants.Constants.*;

import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static Storage storage = new ArrayListStorage();


    public static void main(String[] args) {
        mainMenuChoice();
    }

    private static void mainMenuChoice() {
        System.out.print(HELLO);
        while (true) {
            System.out.println(MAIN_MENU);

            int choice = Integer.parseInt(scan.nextLine());
            switch (choice) {
                case 1: {
                    System.out.println(LOGIN);
                    String login = scan.nextLine();
                    System.out.println(PASSWORD);
                    String password = scan.nextLine();
                    User user = new User(login, password);
                    System.out.println(NAME);
                    user.setName(scan.nextLine());
                    System.out.println(SURNAME);
                    user.setSurname(scan.nextLine());
                    storage.registration(user);
                    break;
                } case 2: {
                    System.out.println(LOGIN);
                    String login = scan.nextLine();
                    System.out.println(PASSWORD);
                    String password = scan.nextLine();
                    User user = new User(login, password);
                    if (storage.authorization(user)) {
                        sideMenuChoice(user);
                    }
                    break;
                } case 3: {
                    System.out.println(GOODBYE);
                    System.exit(0);
                }
            }
        }
    }

    private static void sideMenuChoice(User user) {
        while (true){
            System.out.println(SIDE_MENU);

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
                    System.out.println(ENTER_LOGIN);
                    String loginAnotherUser = scan.nextLine();
                    User anotherEntry = new User(loginAnotherUser);
                    storage.transferMoney(user, anotherEntry, money);
                    break;
                } case 3: {
                    System.out.println(CHANGE_MENU);

                    int choiceChange = Integer.parseInt(scan.nextLine());
                    switch (choiceChange) {
                        case 1: {
                            System.out.println(NEW_NAME);
                            String newName = scan.nextLine();
                            storage.changeYourName(user, newName);
                            break;
                        } case 2: {
                            System.out.println(NEW_SURNAME);
                            String newSurname = scan.nextLine();
                            storage.changeYourSurname(user, newSurname);
                            break;
                        } case 3: {
                            System.out.println(NEW_PASSWORD);
                            String newPassword = scan.nextLine();
                            storage.changeYourPassword(user, newPassword);
                            break;
                        }
                    }
                    break;
                } case 4: {
                    System.out.println(DELETE_ACCOUNT);
                    String answer = scan.nextLine();
                    if (answer.equals(YES)) {
                        storage.deleteAccount(user);
                    }
                    break;
                } case 5: {
                    System.out.println(GOODBYE);
                    System.exit(0);
                }
            }
        }
    }
}

