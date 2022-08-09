package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseCleint{
    @Step("Send POST request to api/auth/register")
    public Response sendPostCreateUser(String body) {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        Response response = burgerRegisterUser.registerNewUserAndReturnResponse(body);
        return response;
    }

    @Step("Send POST request to api/auth/login")
    public Response sendPostLoginUser(String json) {
        Response response = given()
                .spec(getBaseSpec())
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/auth/login");
        return response;
    }

    @Step("Send POST request to api/auth/login")
    public String sendPostLoginUserAndGetBearer(String json) {
        BurgerRegisterUser burgerRegisterUser = new BurgerRegisterUser();
        return burgerRegisterUser.LoginUserAndGetBearer(json);
    }

    @Step("Send PATCH request to api/auth/user")
    public Response refreshUser(String bearer, String json) {
        Response response = given()
                .spec(getBaseSpec())
                .header("Authorization", bearer)
                .body(json)
                .when()
                .patch("api/auth/user");
        return response;
    }

    @Step("Send PATCH request to api/auth/user")
    public Response refreshUser(String json) {
        Response response = given()
                .spec(getBaseSpec())
                .body(json)
                .when()
                .patch("api/auth/user");
        return response;
    }
}
