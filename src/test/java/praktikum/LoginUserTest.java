package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

public class LoginUserTest {
    String jsonCreate;
    UserClient userClient = new UserClient();

    @After
    public void deleteCreateUser() {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.deleteUser(jsonCreate);
    }

    @Test
    @DisplayName("Проверка авторизации пользователя")
    public void checkUserCanLogIn() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        Response response = userClient.sendPostLoginUser(jsonCreate);
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Проверка авторизации пользователя c неправильным логином")
    public void checkUserIncorrectLogin() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String jsonCreateIncorrectLogin = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", "111", pass);
        Response response = userClient.sendPostLoginUser(jsonCreateIncorrectLogin);
        response.then().assertThat().statusCode(401);
    }

    @Test
    @DisplayName("Проверка авторизации пользователя c неправильным паролем")
    public void checkUserIncorrectPass() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String jsonCreateIncorrectLogin = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, "333");
        Response response = userClient.sendPostLoginUser(jsonCreateIncorrectLogin);
        response.then().assertThat().statusCode(401);
    }
}