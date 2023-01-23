package com.example.netengine;

import com.example.netmodel.Request;
import com.example.netmodel.Response;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler {
    private Socket clientSocket;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private Gson gson;

    public ClientHandler(String ip, int port) throws Exception {
        clientSocket = new Socket(InetAddress.getByName(ip), port);

        this.dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
        this.dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());

        this.gson = new Gson();
    }

    public void closeConnection() throws Exception {
        dataInputStream.close();
        dataOutputStream.close();

        clientSocket.close();
    }

    public Response getResponse() throws Exception {
        String responseAsJson = dataInputStream.readUTF();

        return fromJsonToResponse(responseAsJson);
    }

    public void sendRequest(Request request) throws Exception {
        String requestAsJson = fromRequestToJson(request);

        dataOutputStream.writeUTF(requestAsJson);
        dataOutputStream.flush();
    }

    private Response fromJsonToResponse(String responseAsJson) {
        return gson.fromJson(responseAsJson, Response.class);
    }

    private String fromRequestToJson(Request request) {
        return gson.toJson(request);
    }
}
