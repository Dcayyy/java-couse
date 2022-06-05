package com.egt_interactive.model;

public class LoggedState extends ClientState {

    @Override
    public boolean login(String username, String password) {
        throw new RuntimeException("Cannot login.");
    }

    @Override
    public void getAccountDetails() {

    }

    @Override
    public void deposit() {

    }

    @Override
    public void withdraw() {

    }
}
