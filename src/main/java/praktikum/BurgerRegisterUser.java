package praktikum;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class BurgerRegisterUser extends BaseCleint{
    public ArrayList<String> registerNewUserAndReturnLoginPassword() {
        String userLogin = RandomStringUtils.randomAlphabetic(15);
        String userPassword = RandomStringUtils.randomAlphabetic(10);
        String userName = RandomStringUtils.randomAlphabetic(10);

        ArrayList<String> loginPass = new ArrayList<>();

        String registerRequestBody = "{\"email\":\"" + userLogin + "@yandex.ru" + "\","
                + "\"password\":\"" + userPassword + "\","
                + "\"name\":\"" + userName + "\"}";

        Response response = given()
                .spec(getBaseSpec())
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/register");

        if (response.statusCode() == 200) {
            loginPass.add(userLogin);
            loginPass.add(userPassword);
            loginPass.add(userName);
        }

        return loginPass;
    }

    public String buildJsonForRegisterNewUser() {
        String userLogin = RandomStringUtils.randomAlphabetic(15);
        String userPassword = RandomStringUtils.randomAlphabetic(10);
        String userName = RandomStringUtils.randomAlphabetic(10);

        String registerRequestBody = "{\"email\":\"" + userLogin + "@yandex.ru" + "\","
                + "\"password\":\"" + userPassword + "\","
                + "\"name\":\"" + userName + "\"}";

        return registerRequestBody;
    }

    public Response registerNewUserAndReturnResponse (String body) {
        Response response = given()
                .spec(getBaseSpec())
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post("api/auth/register");
        return response;
    }

    public String LoginUserAndGetBearer(String json){
        Response response = given()
                .spec(getBaseSpec())
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/auth/login");
        UserData userBearer = response.body().as(UserData.class);
        String bearer = userBearer.getAccessToken();
        return bearer;
    }
}