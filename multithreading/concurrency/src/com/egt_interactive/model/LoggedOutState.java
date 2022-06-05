package com.egt_interactive.model;


public class LoggedOutState extends ClientState {

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public void getAccountDetails() {
        throw new RuntimeException("Cannot see account details.");
    }

    @Override
    public void deposit() {
        throw new RuntimeException("Cannot deposit.");
    }

    @Override
    public void withdraw() {
        throw new RuntimeException("Cannot see account details.");
    }
}
