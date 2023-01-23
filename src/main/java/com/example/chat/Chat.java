package com.example.chat;

import com.example.model.Message;
import com.example.model.User;
import com.example.netengine.NetStatuses;
import com.example.netmodel.Response;
import com.example.netmodel.ServerException;
import com.example.util.IOUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Chat {
    private User meUser;
    private Gson gson;

    private ServerCommands serverCommands;

    public Chat(ServerCommands serverCommands) {
        this.serverCommands = serverCommands;
        this.gson = new Gson();
    }

    public void authOrRegister() throws Exception {
        IOUtil.println("Приветствуем Вас в почтовом клиенте. Пожалуйста авторизуйтесь или зарегистрируйтесь");

        boolean isDoneAuthOrRegister = false;
        do {
            IOUtil.println("1. Авторизоваться");
            IOUtil.println("2. Зарегистрироваться");
            int action = IOUtil.inputInt("Выберите пунт меню: ", 1, 2);

            switch (action) {
                case 1:
                    isDoneAuthOrRegister = auth();
                    break;

                case 2:
                    isDoneAuthOrRegister = register();
                    break;
            }
        } while (!isDoneAuthOrRegister);
    }

    private boolean auth() throws Exception {
        String login = IOUtil.inputString("Введите свой логин: ", 20);
        String password = IOUtil.inputString("Введите свой пароль: ", 20);

        User user = User.builder()
                .login(login)
                .password(password)
                .build();

        Response response = serverCommands.authUser(user);

        if (response.getStatus() == NetStatuses.OK) {
            meUser = gson.fromJson(response.getJsonData(), User.class);

            //IOUtil.println(meUser.toString());

            IOUtil.println("Вы успешно авторизоватлись. Теперь вы можете переходить к приёму и написанию писем");

            return true;
        } else {
            ServerException exception = gson.fromJson(response.getJsonData(), ServerException.class);
            IOUtil.println(String.format("Ошибка! Код ошибки:%d Текст ошибки:", response.getStatus(), exception.getMessage()));
            return false;
        }
    }

    private boolean register() throws Exception {
        String name = IOUtil.inputString("Введите новое имя: ", 50);
        String login = IOUtil.inputString("Введите новый логин: ", 20);
        String password = IOUtil.inputString("Введите новый пароль: ", 20);

        User user = User.builder()
                .name(name)
                .login(login)
                .password(password)
                .build();

        Response response = serverCommands.registerUser(user);

        if (response.getStatus() == NetStatuses.OK) {
            meUser = gson.fromJson(response.getJsonData(), User.class);

            //IOUtil.println(meUser.toString());

            IOUtil.println("Вы успешно зарегистрировались. Теперь вы можете переходить к приёму и написанию писем");

            return true;
        } else {
            ServerException exception = gson.fromJson(response.getJsonData(), ServerException.class);
            IOUtil.println(String.format("Ошибка! Код ошибки:%d Текст ошибки:", response.getStatus(), exception.getMessage()));

            return false;
        }
    }

    public void doChat() throws Exception {
        List<User> usersWithoutMe = new ArrayList<>();

        IOUtil.println(String.format("Приветствуем Вас %s (ИД:%d). Выберите действие дляуправления почтовым клиентом.", meUser.getName(), meUser.getId()));

        boolean isChatting = true;
        do {
            IOUtil.println("1. Просмотреть список всех пользователей");
            IOUtil.println("2. Написать письмо пользователю");
            IOUtil.println("3. Получить список входящих писем");
            IOUtil.println("0. Закончить работу с почтовым клиентом");
            int action = IOUtil.inputInt("Выберите пунт меню: ", 0, 3);

            switch (action) {
                case 1: {
                    usersWithoutMe = getAllUsersWithoutMe();
                    printAllUsersWithoutMe(usersWithoutMe);
                }
                break;

                case 2: {
                    if (usersWithoutMe.size() == 0) {
                        IOUtil.println("Ошибка. Вначале получите список всех пользователей");
                        break;
                    }
                    sendMessage(usersWithoutMe);
                }
                break;

                case 3: {

                }
                break;

                case 0: {
                    isChatting = false;
                }
                break;

                default: {
                    IOUtil.println("Ошибка. Введён неизвестный пункт меню.");
                }
                break;
            }
        } while (isChatting);

        disconnect();

    }

    private void disconnect() throws Exception {
        Response response = serverCommands.disconnectUser(meUser);

        if (response.getStatus() == NetStatuses.OK) {
            IOUtil.println("Вы успешно вышли из сети");
        } else {
            ServerException exception = gson.fromJson(response.getJsonData(), ServerException.class);
            IOUtil.println(String.format("Ошибка! Код ошибки:%d Текст ошибки:", response.getStatus(), exception.getMessage()));
        }

        serverCommands.closeConnection();
    }


    private List<User> getAllUsersWithoutMe() throws Exception {
        Response response = serverCommands.getAllUsersWithoutMe(meUser);

        if (response.getStatus() == NetStatuses.OK) {
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            List<User> users = gson.fromJson(response.getJsonData(), listType);

            return users;

        } else {
            ServerException exception = gson.fromJson(response.getJsonData(), ServerException.class);
            IOUtil.println(String.format("Ошибка! Код ошибки:%d Текст ошибки:", response.getStatus(), exception.getMessage()));

            throw new Exception(exception.getMessage());
        }
    }

    private void printAllUsersWithoutMe(List<User> users) {
        IOUtil.println(String.format("%-3s%-20s%-10s", "ИД", "Имя пользователя", "В сети"));
        users.forEach(user -> {
            IOUtil.println(String.format("%-3s%-20s%-10s",
                    user.getId(), user.getName(), user.isOnline() ? "да" : "нет"));
        });
    }

    private void sendMessage(List<User> usersWithoutMe) throws Exception {

        List<Long> ids = usersWithoutMe.stream().map(
                user -> user.getId()
        ).collect(Collectors.toList());

        String text = IOUtil.inputString("Введите текст сообщения: ", 2048);
        long toUserId = IOUtil.inputLong("ИД получателя: ", ids);

        Message message = Message.builder()
                .fromUser(
                        User.builder().id(meUser.getId()).build()
                )
                .toUser(
                        User.builder().id(toUserId).build()
                )
                .text(text)
                .build();

        Response response = serverCommands.sendMessage(message);

        if (response.getStatus() == NetStatuses.OK) {
            IOUtil.println("Сообщение успешно отправлено");
        } else {
            ServerException exception = gson.fromJson(response.getJsonData(), ServerException.class);
            IOUtil.println(String.format("Ошибка! Код ошибки:%d Текст ошибки:", response.getStatus(), exception.getMessage()));
        }
    }
}
