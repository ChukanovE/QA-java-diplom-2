package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseCleint{
    @Step("Send POST request to api/auth/login")
    public String sendPostLoginUserAndGetBearer(String json) {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        return burgerRegisterUser.LoginUserAndGetBearer(json);
    }

    @Step("Send get request to api/orders")
    public Response sendGetListOrder(String bearer) {
        Response response = given()
                .spec(getBaseSpec())
                .header("Authorization", bearer)
                .when()
                .get("api/orders");
        return response;
    }

    @Step("Send POST request to api/orders")
    public Response sendPostCreateOrder(String json, String bearer) {
        Response response = given()
                .spec(getBaseSpec())
                .header("Authorization", bearer)
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/orders");
        return response;
    }
}
