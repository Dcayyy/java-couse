package com.egt_interactive.model;

import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private Client client;
    private double balance;
    private String username;
    private String password;
    ReentrantLock lock = new ReentrantLock();

    public Account(Client client, double balance, String username, String password) {
        this.client = client;
        this.balance = balance;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            if (amount > 0) {
                balance += amount;
            }
        } finally {
            lock.unlock();
        }

    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            if (balance - amount >= 0) {
                balance -= amount;
            }
        } finally {
            lock.unlock();
        }
    }
}
