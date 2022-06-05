package com.egt_interactive.model;

import java.util.List;

public class MockAccounts {
    public static final List<Account> bankClients = List.of(
            new Account(new Client("Zahari", 20), 500, "Dcayy", "12345"),
            new Account(new Client("Rosen", 20), 500, "Rosen", "9102384"),
            new Account(new Client("Georgi", 25), 1500, "Goshoo", "543421")
    );
}
