package com.egt_interactive.client;

import com.egt_interactive.model.Account;
import com.egt_interactive.model.ClientState;
import com.egt_interactive.model.LoggedOutState;
import com.egt_interactive.model.LoggedState;
import com.egt_interactive.server.BankServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BankClient implements Client {
    private Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    private ConnectionSettings settings;
    private InetAddress address;
    private volatile boolean canceled = false;
    private static BankClient client = new BankClient();
    private static Scanner sc = new Scanner(System.in);
    private Account account;
    private ClientState clientState;

    public BankClient() {
        setState(new LoggedOutState());
    }

    public void setState(ClientState clientState) {
        this.clientState = clientState;
    }

    public static void main(String[] args) {
        BankClient client1 = new BankClient();
        login();
    }

    private static void login() {
        String username = "";
        String password = "";
        boolean flag = false;
        do {
            System.out.print("Enter your username:");
            username = sc.nextLine();
            System.out.print("Enter your password:");
            password = sc.nextLine();

            // create client
            var settings = new ConnectionSettings("localhost", BankServer.PORT, username, password);

            // login client -> in, out initialized
            var errorMessage = client.connectToServer(settings);
            if (errorMessage != null) {
                System.out.println(errorMessage);
            } else {
                flag = true;
            }
        } while (!flag);
    }

    @Override
    public String connectToServer(ConnectionSettings settings) {
        this.settings = settings;
        try {
            address = InetAddress.getByName(settings.getHost());
            socket = new Socket(address, settings.getPort());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            client.sendMessage(settings.getUsername());     // Sending message  (1)
            client.sendMessage(settings.getPassword());     // Sending password (2)

            String isLoggedIn = in.readLine();  // Reading message (1)

            if (!isLoggedIn.equals("Incorrect credentials")) {
                System.out.println(String.format("User %s successfully logged in to server: %s", settings.getUsername(), socket));
                client.setState(new LoggedState());
                return null;
            }
            return "Incorrect credentials";
        } catch (UnknownHostException e) {
            System.out.println(String.format("Unknown git server hostname:", e));
            return "Unknown git server hostname: " + settings.getHost();
        } catch (IOException e) {
            System.out.println(String.format("Error connecting to git server:", e));
            return "Error connecting to git server: " + address + ":" + settings.getPort();
        }
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public boolean logout() {
        return true;
    }
}
