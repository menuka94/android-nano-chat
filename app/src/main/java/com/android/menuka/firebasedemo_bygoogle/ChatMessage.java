package com.android.menuka.firebasedemo_bygoogle;

/**
 * Created by menuka on 1/24/17.
 */

public class ChatMessage {
    String name;
    String message;

    public ChatMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public ChatMessage() {

    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
