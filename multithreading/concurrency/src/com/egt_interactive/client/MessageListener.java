package com.egt_interactive.client;

// implements Observer pattern
public interface MessageListener {
    void onMessage(String message);
    void onError(String error);
}
