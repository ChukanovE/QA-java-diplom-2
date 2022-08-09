package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class RefreshUserWithAuthTest {
    String jsonCreate;
    UserClient userClient = new UserClient();

    @After
    public void deleteCreateUser() {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.deleteUser(jsonCreate);
    }

    @Test
    @DisplayName("Проверка изменения имени пользователя")
    public void checkUserCanRename() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String bearer = userClient.sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "[ {\"op\": \"replace\", \"path\": \"/name\", \"value\": \"Viz\"}]";
        Response response = userClient.refreshUser(bearer, json);
        String body = "<{email=" + login + "@yandex.ru, name=Viz>}";
        response.then().assertThat().body("user", equalTo(body)).statusCode(200);
    }

    @Test
    @DisplayName("Проверка изменения логина пользователя")
    public void checkUserCanChangeLogin() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String name = Arr.get(2);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String bearer = userClient.sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "[ {\"op\": \"replace\", \"path\": \"/email\", \"value\": \"123456789@Gmail.com\"}]";
        Response response = userClient.refreshUser(bearer, json);
        String body = "<{email=123456789@gmail.com, name=" + name + "}>";
        response.then().assertThat().body("user", equalTo(body)).statusCode(200);
    }
}