package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

public class CreateOrderWithoutAuthTest {
    String bearer = "";
    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Проверка создания заказа с индигриентами без авторизации")
    public void checkCreateOrderWithoutAuthorization() {

        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6f\"]}";
        Response response = orderClient.sendPostCreateOrder(json, bearer);
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Проверка создания заказа без индигриентов и без авторизации")
    public void checkCreateOrderWithoutAuthorizationAndWithoutIngredients() {
        String json = "{\"ingredients\": []}";
        Response response = orderClient.sendPostCreateOrder(json, bearer);
        response.then().assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Проверка создания заказа с неправильными хешами индигриентов и без авторизации")
    public void checkCreateOrderWithoutAuthorizationAndWithBadHashIngredients() {
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaa23a6d\",\"61c0c5a71d1f82001b123daaa6f\"]}";
        Response response = orderClient.sendPostCreateOrder(json, bearer);
        response.then().assertThat().statusCode(500);
    }
}