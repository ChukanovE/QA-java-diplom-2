package praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CreateOrderWithAuthTest {
    String jsonCreate;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

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
        System.out.println(jsonCreate);
        String bearer = sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6f\"]}";
        Response response = sendPostCreateOrder(json, bearer);
        printResponseBodyToConsole(response);
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
        System.out.println(jsonCreate);
        String bearer = sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "";
        Response response = sendPostCreateOrder(json, bearer);
        printResponseBodyToConsole(response);
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
        System.out.println(jsonCreate);
        String bearer = sendPostLoginUserAndGetBearer(jsonCreate);
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaa23a6d\",\"61c0c5a71d1f82001b123daaa6f\"]}";
        Response response = sendPostCreateOrder(json, bearer);
        printResponseBodyToConsole(response);
        response.then().assertThat().statusCode(500);
    }


    @Step("Send POST request to api/auth/login")
    public String sendPostLoginUserAndGetBearer(String json) {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        return burgerRegisterUser.LoginUserAndGetBearer(json);
    }

    @Step("Send POST request to api/orders")
    public Response sendPostCreateOrder(String json, String bearer) {
        Response response = given()
                .header("Authorization", bearer)
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/orders");
        return response;
    }

    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response) {
        System.out.println(response.body().asString());
    }
}
