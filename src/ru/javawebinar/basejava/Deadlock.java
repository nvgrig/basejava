package ru.javawebinar.basejava;

import ru.javawebinar.basejava.exception.StorageException;

public class Deadlock {
    public static void main(String[] args) {
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);

        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                Operations.transfer(account1, account2, 1);
            }
        }).start();

        for (int i = 0; i < 500; i++) {
            Operations.transfer(account2, account1, 1);
        }
    }

}

class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }
}

class Operations {
    public static void transfer(Account account1, Account account2, int amount) throws StorageException {
        if (account1.getBalance() < amount) {
            throw new StorageException("Insufficient funds");
        }
        synchronized (account1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (account2) {
                account1.withdraw(amount);
                account2.deposit(amount);
            }
        }
        System.out.println("Transfer complete");
    }
}