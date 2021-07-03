import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.*;

import org.testng.annotations.Test;

public class PostUserTests extends TestBase{

    private User postUser;
    private User sameEmail1;
    private User sameEmail2;

    @BeforeClass
    public void generateTestData(){
        //Massa para o teste status code 201
        postUser = new User("Samuel", "samuel@email.com", "123", "true");

        //Massa para o teste status code 400 já inserindo um usuário
        sameEmail1 = new User("Samuel Aquino", "samuelaquino@email.com", "987", "false");
        registerUserRequest(SPEC, sameEmail1);

        //Massa para o teste status code 400 em que o usuário com mesmo email ja cadastradao será inserido no próprio teste
        sameEmail2 = new User("Samuel Aquino", "samuelaquino@email.com", "987", "false");
    }

    //Deleção das massas após o teste
    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, postUser);
        deleteUserRequest(SPEC, sameEmail1);
        deleteUserRequest(SPEC, sameEmail2);
    }

    //Cadastro com sucesso status code 201
    @Test
    public void shouldReturnSuccessMessageRegisterNewUserAndStatus201(){
        Response postUserSuccessResponse = registerUserRequest(SPEC, postUser);
        postUserSuccessResponse.
                then().
                log().all().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_INSERT_NEW_USER));
    }

    //E-mail já cadastrado status code 400
    @Test
    public void shouldReturnFailureMessageRegisterUserSameEmailAndStatus400(){
        Response postUserFailureSameEmailResponse = registerUserRequest(SPEC, sameEmail2);
        postUserFailureSameEmailResponse.
                then().
                log().all().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_FAILED_INSERT_NEW_USER_WITH_THE_SAME_EMAIL));
    }
}
