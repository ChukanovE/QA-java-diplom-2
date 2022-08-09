package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

public class RefreshUserWithoutAuthTest {
    UserClient userClient = new UserClient();

    @Test
    @DisplayName("Проверка изменения логина пользователя без авторизации")
    public void checkUserCanChangeLoginWithoutAuthorization() {
        String json = "[ {\"op\": \"replace\", \"path\": \"/email\", \"value\": \"123456789@Gmail.com\"}]";
        Response response = userClient.refreshUser(json);
        response.then().assertThat().statusCode(401);
    }
}