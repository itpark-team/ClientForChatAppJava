package com.example;

import com.example.chat.ApiWorker;
import com.example.chat.Chat;
import com.example.netengine.ClientHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        ApiWorker apiWorker = new ApiWorker(new ClientHandler("127.0.0.1", 23256));

        Chat chat = new Chat(apiWorker);

        chat.authOrRegister();
        chat.doChat();
    }
}
