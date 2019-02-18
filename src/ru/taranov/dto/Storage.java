package ru.taranov.dto;

public interface Storage {

   void registration();
   void authorization();
   void increaseTheDeposit(User user);
   void transferMoney(User user, User anotherUser);
   void changeYourData(User user);
   void deleteAccount(User user);
}
