package api.services.users;

import api.dto.RegisterUserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertyLoader;

public class RegistrationSpec {
    private final PropertyLoader propertyLoader = new PropertyLoader();

    public Response getResponse(RegisterUserDto registerUserData) {
        return spec()
            .body(registerUserData)
            .post(propertyLoader.getProperty("REGISTER_ENDPOINT"));
    }

    public Response getResponse(String registerUserData) {
        return spec()
            .body(registerUserData)
            .post(propertyLoader.getProperty("REGISTER_ENDPOINT"));
    }

    private RequestSpecification spec() {
        return RestAssured.given()
                          .baseUri(propertyLoader.getProperty("BASE_URL"))
                          .contentType(ContentType.JSON);
    }
}
