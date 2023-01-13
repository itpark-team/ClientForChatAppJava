package com.example;

import com.example.netengine.ClientHandler;
import com.example.netmodel.Request;
import com.example.netmodel.Response;

public class Main {
    public static void main(String[] args) throws Exception {
        ClientHandler clientHandler = new ClientHandler("127.0.0.1", 23256);

        Request request = Request.builder().command("empty").jsonData("cba").build();

        clientHandler.sendRequest(request);

        Response response = clientHandler.getResponse();

        System.out.println(response);
    }
}
