package com.egt_interactive.model;


import java.util.List;

public class Bank {
    private String name;
    private List<Account> accounts;

    public Bank(String name, List<Account> accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
