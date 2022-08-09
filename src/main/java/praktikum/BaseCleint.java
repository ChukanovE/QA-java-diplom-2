package praktikum;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseCleint {
    private final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    public RequestSpecification getBaseSpec() {
        RestAssured.baseURI = BASE_URI;
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }
}
