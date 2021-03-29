package microservices.sock.shop.users;

import api.dto.RegisterUserDto;
import api.services.users.RegistrationSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.junit.Assert.assertThat;
import static utils.TestDataHelper.getTestData;

public class RegisterPostTest {

    private final RegistrationSpec registrationSpec = new RegistrationSpec();

    private RegisterUserDto userTestData;

    @Test
    void validateUserRegistrationIdNotEmptyOrNull() {
        userTestData = getTestData();
        Response registrationResponse = registrationSpec.getResponse(userTestData);
        assertThat("Login status code not as expected.", registrationResponse.statusCode(), is(SC_OK));
        assertThat("User id is Empty or Null.", registrationResponse.jsonPath().getString("id"), is(not(emptyOrNullString())));
    }

    @Test
    void validateUserPasswordNotMandatory() {
        userTestData = getTestData();
        userTestData.setPassword(EMPTY);
        registrationSpec.getResponse(userTestData).then().assertThat().statusCode(SC_OK);
    }

    @Test
    void validateOnlyUniqueUsernameAllowed() {
        userTestData = getTestData();
        registrationSpec.getResponse(userTestData)
                        .then()
                        .assertThat()
                        .statusCode(SC_OK);

        RegisterUserDto duplicateUserTestData = getTestData();
        duplicateUserTestData.setUsername(userTestData.getUsername());
        registrationSpec.getResponse(duplicateUserTestData)
                        .then()
                        .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test()
    void validateEmptyUsernameNotAllowed() {
        userTestData = getTestData();
        userTestData.setUsername(EMPTY);

        registrationSpec.getResponse(userTestData)
                        .then()
                        .assertThat()
                        .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test()
    void validateNullUsernameNotAllowed() {
        userTestData = getTestData();
        userTestData.setUsername(null);

        registrationSpec.getResponse(userTestData)
                        .then()
                        .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test()
    void validateEmptyBodyNotAllowed() {
        registrationSpec.getResponse(EMPTY)
                        .then()
                        .assertThat()
                        .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
