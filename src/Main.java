import ru.taranov.dto.impl.HashSetStorage;
import ru.taranov.dto.Storage;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        mainMenuChoice();
    }

    private static void mainMenuChoice() {
        Scanner scan = new Scanner(System.in);
        Storage storage = new HashSetStorage();

        System.out.print("Hello, ");
        while (true) {
            System.out.println("choose a number");
            System.out.println("1)registration");
            System.out.println("2)authorization");
            System.out.println("3)exit");

            int choice = Integer.parseInt(scan.nextLine());
            switch (choice) {
                case 1: {
                    storage.registration();
                    break;
                } case 2: {
                    storage.authorization();
                    break;
                } case 3: {
                    System.out.println("goodbye");
                    System.exit(0);
                }
            }
        }
    }
}
