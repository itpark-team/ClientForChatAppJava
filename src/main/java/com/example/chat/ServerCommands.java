package com.example.chat;

import com.example.dto.message.*;
import com.example.dto.user.*;
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

    public Response authUser(AuthUserRequestDto authUser) throws Exception {

        String jsonData = gson.toJson(authUser);

        Request request = Request.builder()
                .command(NetCommands.AUTH_USER)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response registerUser(RegisterUserRequestDto registerUser) throws Exception {

        String jsonData = gson.toJson(registerUser);

        Request request = Request.builder()
                .command(NetCommands.REGISTER_USER)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response disconnectUser(DisconnectUserRequestDto meUser) throws Exception {

        String jsonData = gson.toJson(meUser);

        Request request = Request.builder()
                .command(NetCommands.DISCONNECT_USER)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response getAllUsersWithoutMe(UsersWithoutMeRequestDto meUser) throws Exception {
        String jsonData = gson.toJson(meUser);

        Request request = Request.builder()
                .command(NetCommands.GET_ALL_USERS_WITHOUT_ME)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response sendMessage(AddNewMessageRequestDto message) throws Exception {

        String jsonData = gson.toJson(message);

        Request request = Request.builder()
                .command(NetCommands.ADD_NEW_MESSAGE)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public Response getUncheckedMessages(GetUncheckedMessagesRequestDto meUser) throws Exception {

        String jsonData = gson.toJson(meUser);

        Request request = Request.builder()
                .command(NetCommands.GET_UNCHECKED_MESSAGES)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();

    }

    public Response setMessagesStatusIsOpened(SetMessagesStatusIsOpenedRequestDto meUser) throws Exception {
        String jsonData = gson.toJson(meUser);

        Request request = Request.builder()
                .command(NetCommands.SET_MESSAGES_STATUS_IS_OPENED)
                .jsonData(jsonData)
                .build();

        clientHandler.sendRequest(request);
        return clientHandler.getResponse();
    }

    public void closeConnection() throws Exception {
        clientHandler.closeConnection();
    }
}
