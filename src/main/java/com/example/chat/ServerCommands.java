package com.example.chat;

import com.example.model.Message;
import com.example.model.User;
import com.example.netengine.ClientHandler;
import com.example.netengine.NetCommands;
import com.example.netmodel.Request;
import com.example.netmodel.Response;
import com.google.gson.Gson;

public class ServerCommands {
    private Gson gson;
    private ClientHandler clientHandler;

    public ServerCommands(ClientHandler clientHandler) {
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

    public Response registerUser(User registerUser) throws Exception {

        String jsonData = gson.toJson(registerUser);

        Request request = Request.builder()
                .command(NetCommands.REGISTER_USER)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response disconnectUser(User meUser) throws Exception {

        String jsonData = gson.toJson(meUser);

        Request request = Request.builder()
                .command(NetCommands.DISCONNECT_USER)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response getAllUsersWithoutMe(User meUser) throws Exception {
        String jsonData = gson.toJson(meUser);

        Request request = Request.builder()
                .command(NetCommands.GET_ALL_USERS_WITHOUT_ME)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response sendMessage(Message message) throws Exception {

        String jsonData = gson.toJson(message);

        Request request = Request.builder()
                .command(NetCommands.ADD_NEW_MESSAGE)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public void closeConnection() throws Exception {
        clientHandler.closeConnection();
    }
}
