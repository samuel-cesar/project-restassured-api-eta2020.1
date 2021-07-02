import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requests.UserEndpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.*;
import static requests.UserEndpoint.authenticateUserRequest;

public class PostLoginTests extends TestBase{

    private User validUser;
    private User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Mari", "mari@email.com", "123abc", "true");
        registerUserRequest(SPEC, validUser);
        invalidUser = new User("Mari", "mari@email.com", "oiuyui", "true");
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }

    @Test
    public void shouldReturnSuccessMessageAuthTokenAndStatus200(){
        Response loginSuccessResponse = authenticateUserRequest(SPEC, validUser);
        loginSuccessResponse.
                then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_LOGIN)).
                body("authorization", notNullValue());

    }

    @Test
    public void shouldReturnFailureMessageAndStatus401(){

        Response loginFailureResponse = authenticateUserRequest(SPEC, invalidUser);
        loginFailureResponse.
                then().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_LOGIN)).
                body("authorization", nullValue());
    }
}
