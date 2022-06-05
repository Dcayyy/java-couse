package com.egt_interactive.model;

import com.egt_interactive.client.Client;

public abstract class ClientState {

    public abstract boolean login(String username, String password);
    public abstract void getAccountDetails();
    public abstract void deposit();
    public abstract void withdraw();
}
