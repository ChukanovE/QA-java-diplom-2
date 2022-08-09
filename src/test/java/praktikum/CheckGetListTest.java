package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

public class CheckGetListTest {
    String jsonCreate;
    OrderClient orderClient = new OrderClient();

    @After
    public void deleteCreateUser() {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.deleteUser(jsonCreate);
    }

    @Test
    @DisplayName("Проверка получения списка заказов пользователя с авторизацией")
    public void checkGetListOrderWithAuthorization() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String bearer = orderClient.sendPostLoginUserAndGetBearer(jsonCreate);
        Response response = orderClient.sendGetListOrder(bearer);
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Проверка получения списка заказов пользователя без авторизации")
    public void checkGetListOrderWithoutAuthorization() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String bearer = "";
        Response response = orderClient.sendGetListOrder(bearer);
        response.then().assertThat().statusCode(401);
    }
}