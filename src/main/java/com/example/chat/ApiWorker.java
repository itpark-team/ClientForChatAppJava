package com.example.chat;

import com.example.model.User;
import com.example.netengine.ClientHandler;
import com.example.netengine.NetCommands;
import com.example.netmodel.Request;
import com.example.netmodel.Response;
import com.google.gson.Gson;

public class ApiWorker {
    private Gson gson;
    private ClientHandler clientHandler;

    public ApiWorker(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.gson = new Gson();
    }

    public Response authUser(User authUser) throws Exception {

        String jsonData = gson.toJson(authUser);

        Request request = Request.builder()
                .command(NetCommands.AUTH_USER)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }
}
