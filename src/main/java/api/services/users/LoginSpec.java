package api.services.users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.PropertyLoader;

public class LoginSpec {
    private final PropertyLoader propertyLoader = new PropertyLoader();

    public Response getLoginResponse(String userName, String password) {
        return RestAssured.given()
                          .baseUri(propertyLoader.getProperty("BASE_URL"))
                          .contentType(ContentType.JSON)
                          .when()
                          .auth()
                          .preemptive()
                          .basic(userName, password)
                          .get(propertyLoader.getProperty("LOGIN_ENDPOINT"));
    }
}
