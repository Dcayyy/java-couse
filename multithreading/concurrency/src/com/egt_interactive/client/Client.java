package com.egt_interactive.client;

public interface Client {
    String connectToServer(ConnectionSettings settings);
    void sendMessage(String message);
    boolean logout();
/*    void addMessageListener(MessageListener listener); // Observer pattern
    void removeMessageListener(MessageListener listener);*/
}
