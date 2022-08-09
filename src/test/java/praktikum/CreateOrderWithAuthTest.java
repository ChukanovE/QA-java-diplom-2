package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

public class CreateOrderWithAuthTest {
    String jsonCreate;
    OrderClient orderClient = new OrderClient();

    @After
    public void deleteCreateUser() {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.deleteUser(jsonCreate);
    }

    @Test
    @DisplayName("Проверка создания заказа с ингридиентами")
    public void checkCreateOrderWithAuthorizationAndIngredients() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String bearer = orderClient.sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6f\"]}";
        Response response = orderClient.sendPostCreateOrder(json, bearer);
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Проверка создания заказа без индигриентов")
    public void checkCreateOrderWithAuthorizationAndWithoutIngredients() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String bearer = orderClient.sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "";
        Response response = orderClient.sendPostCreateOrder(json, bearer);
        response.then().assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Проверка создания заказа с неправильными хешами индигриентов")
    public void checkCreateOrderWithAuthorizationAndWithBadHashIngredients() {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        ArrayList<String> Arr = burgerRegisterUser.registerNewUserAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = String.format("{\"email\":\"%s@yandex.ru\", \"password\":\"%s\"}", login, pass);
        String bearer = orderClient.sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaa23a6d\",\"61c0c5a71d1f82001b123daaa6f\"]}";
        Response response = orderClient.sendPostCreateOrder(json, bearer);
        response.then().assertThat().statusCode(500);
    }
}