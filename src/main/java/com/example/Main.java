package com.example;

import com.example.chat.ServerCommands;
import com.example.chat.Chat;
import com.example.netengine.ClientHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        ServerCommands serverCommands = new ServerCommands(new ClientHandler("127.0.0.1", 23256));

        Chat chat = new Chat(serverCommands);

        chat.authOrRegister();
        chat.doChat();
    }
}
