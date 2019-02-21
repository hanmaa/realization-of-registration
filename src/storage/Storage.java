package storage;

import ru.taranov.dto.User;

public interface Storage {

   void registration(User user);
   boolean authorization(User user);
   void increaseTheDeposit(User user, int addition);
   void transferMoney(User user, User anotherEntry, double money);
   void changeYourPassword(User user, String newPassword);
   void changeYourName(User user, String newName);
   void changeYourSurname(User user, String newSurname);
   void deleteAccount(User user);
}
