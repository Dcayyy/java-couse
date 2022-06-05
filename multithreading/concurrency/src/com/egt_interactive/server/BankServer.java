package com.egt_interactive.server;

import com.egt_interactive.model.Account;
import com.egt_interactive.model.MockAccounts;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankServer implements Runnable {
    public static final int PORT = 9090;

    private volatile boolean canceled = false;

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Collection<Handler> handlers = new CopyOnWriteArrayList<>();

    public void cancel() {
        this.canceled = true;
    }

    @Override
    public void run() {
        try (ServerSocket ssoc = new ServerSocket(PORT, -1,
                InetAddress.getByAddress(new byte[]{127, 0, 0, 1}))) {
            System.out.println(String.format("Git Server is listening for connections on: %s", ssoc));
            while (!canceled && !Thread.interrupted()) {
                try {
                    Socket s = ssoc.accept();
                    System.out.println(String.format("Git Server connection accepted: %s", s));
                    var handler = new Handler(this, s);
                    handlers.add(handler);
                    executor.execute(handler);
                } catch (IOException e) {
                    System.out.println(String.format("Error accepting client connection:", e));
                }
            }
            System.out.println(String.format("Closing Git Server."));
        } catch (IOException e) {
            System.out.println(String.format("Error running Git Server:", e));
            throw new RuntimeException(e);
        } finally {
            executor.shutdownNow();
        }
    }

    public void removeHandler(Handler handler) {
        handlers.remove(handler);
    }

    public static void main(String[] args) {
        new BankServer().run();
    }
}

class Handler implements Runnable {
    private BankServer server;
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Handler(BankServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public boolean login(Account loggedAccount, String username, String password) {
        if (loggedAccount.getUsername().equals(username) && loggedAccount.getPassword().equals(password)) {
            System.out.println(String.format("Successfully logged in User %s", loggedAccount.getUsername()));
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            String username = "";
            String password = "";

            Account account;
            while (true) {
                // login application protocol: 1) read username as first message
                username = in.readLine();
                // login application protocol: 2) read password as second message
                password = in.readLine();

                try {
                    Account loggedAccount = getAccount(username);
                    if (loggedAccount != null) {
                        if (login(loggedAccount, username, password)) {
                            sendMessage("Successfully logged in User " + loggedAccount.getUsername());
                            break;
                        }
                    }
                } catch (RuntimeException e) {
                    sendMessage("Incorrect credentials");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading/writing from/to TCP socket:", e);
        }
    }

    public Account getAccount(String username) {
        return MockAccounts.bankClients.stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst().get();
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
