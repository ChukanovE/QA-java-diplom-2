package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    String jsonCreate;
    UserClient userClient = new UserClient();
    BurgerRegisterUser burgerRegisterCourier = new BurgerRegisterUser();

    @After
    public void deleteCreateUser() {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.deleteUser(jsonCreate);
    }

    @Test
    @DisplayName("Проверка - пользоваетля можно создать и успешный запрос возращает: success: true")
    public void checkCreateUser() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        jsonCreate = burgerRegisterUser.buildJsonForRegisterNewUser();
        Response response = userClient.sendPostCreateUser(jsonCreate);
        response.then().assertThat().body("success", equalTo(true)).statusCode(200);
    }

    @Test
    @DisplayName("Проверка создания двух одинаковых пользователей")
    public void checkCreateAnAlreadyRegisteredUser() {
        BurgerRegisterUser burgerRegisterCourier = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterCourier.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String name = Arr.get(2);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String jsonWithSameLogin = String.format("{\"email\":\"%s@yandex.ru\",\"password\":\"%s\",\"name\":\"%s\"}", login, "pass", name);
        Response response = userClient.sendPostCreateUser(jsonWithSameLogin);
        response.then().assertThat().body("message", equalTo("User already exists")).statusCode(403);
    }

    @Test
    @DisplayName("Проверка создание пользователя без логина")
    public void checkCreatingUserWithoutLogin() {
        BurgerRegisterUser burgerRegisterCourier = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterCourier.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String name = Arr.get(2);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String jsonWithoutLogin = String.format("{\"password\":\"%s\",\"name\":\"%s\"}", pass, name);
        Response response = userClient.sendPostCreateUser(jsonWithoutLogin);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields")).statusCode(403);
    }

    @Test
    @DisplayName("Проверка создание пользователя без пароля")
    public void checkCreatingUserWithoutPass() {
        BurgerRegisterUser burgerRegisterCourier = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterCourier.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String name = Arr.get(2);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String jsonWithoutPass = String.format("{\"email\":\"%s@yandex.ru\",\"name\":\"%s\"}", login, name);
        Response response = userClient.sendPostCreateUser(jsonWithoutPass);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields")).statusCode(403);
    }

    @Test
    @DisplayName("Проверка создание пользователя без имени")
    public void checkCreatingUserWithoutName() {
        BurgerRegisterUser burgerRegisterCourier = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterCourier.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String getJsonWithoutName = String.format("{\"email\":\"%s@yandex.ru\",\"password\":\"%s\"}", login, pass);
        Response response = userClient.sendPostCreateUser(getJsonWithoutName);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields")).statusCode(403);
    }
}