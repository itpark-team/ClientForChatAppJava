package com.example.chat;

import com.example.model.User;
import com.example.netengine.NetStatuses;
import com.example.netmodel.Response;
import com.example.util.IOUtil;
import com.google.gson.Gson;

public class Chat {
    private User meUser;
    private Gson gson;

    private ApiWorker apiWorker;

    public Chat(ApiWorker apiWorker) {
        this.apiWorker = apiWorker;
        this.gson = new Gson();
    }

    public void authOrRegister() throws Exception {
        IOUtil.println("Приветствуем вас в чате. Пожалуйста авторизуйтесь или зарегистрируйтесь");

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

        Response response = apiWorker.authUser(user);

        if (response.getStatus() == NetStatuses.OK) {
            meUser = gson.fromJson(response.getJsonData(), User.class);

            IOUtil.println(meUser.toString());

            IOUtil.println("Вы успешно авторизоватлись. Теперь вы можете переходить к общению");

            return true;
        } else if (response.getStatus() == NetStatuses.USER_NOT_FOUND) {

            IOUtil.println("Пользователя с таким логином и паролем не существует");

            return false;
        }

        throw new Exception("Unexpected Net Status");
    }

    private boolean register() {
        return false;
    }

    public void doChat() {

    }

}
