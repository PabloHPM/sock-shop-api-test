package microservices.sock.shop.users;

import api.services.users.LoginSpec;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;

public class LoginGetTest {

    private final LoginSpec loginSpec = new LoginSpec();


    @DataProvider(name = "PositiveLoginParameters")
    public Object[][] getPositiveLoginParameters() {
        return new Object[][]{{"user", "password"},
                              {"user1", "password"},
                              {"Eve_Berger", "eve"}};
    }

    @Test(dataProvider = "PositiveLoginParameters")
    public void validateValidLoginParameters(String userName, String password) {
        loginSpec.getLoginResponse(userName, password)
                 .then()
                 .assertThat()
                 .statusCode(SC_OK);
    }

    @Test
    public void validateResponseCookieNotEmptyOrNull() {
        ValidatableResponse loginResponse =
            loginSpec.getLoginResponse("user", "password")
                     .then()
                     .assertThat()
                     .statusCode(SC_OK);

        assertThat("'Cookie' is Empty or Null.", loginResponse.extract().cookie("logged_in"),
                   is(not(emptyOrNullString())));
    }

    @DataProvider(name = "NegativeLoginParameters")
    public Object[][] getNegativeLoginParameters() {
        return new Object[][]{{"user", ""},
                              {"USER", "password"},
                              {"Eve_Berger", "password"},
                              {"$#$#$", "#$#$#$"}};
    }

    @Test(dataProvider = "NegativeLoginParameters")
    public void validateInvalidLoginParameters(String userName, String password) {
        loginSpec.getLoginResponse(userName, password)
                 .then()
                 .assertThat()
                 .statusCode(SC_UNAUTHORIZED);
    }
}
