import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static requests.ProductEndPoint.*;
import static requests.UserEndpoint.*;
import static requests.UserEndpoint.deleteUserRequest;

public class PostProdutoTests extends TestBase{

    private User autenticaUser;
    private Product postProduct;

    @BeforeClass
    public void generateTestData(){

        autenticaUser = new User("Samuel Autenticar Com ApiKey", "samuel_autentica@email.com", "123", "true");
        registerUserRequest(SPEC, autenticaUser);

        Response loginSuccessResponse = authenticateUserRequest(SPEC, autenticaUser);
        loginSuccessResponse.
                then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_LOGIN)).
                body("authorization", notNullValue());

        postProduct = new Product("Produto PostProduct 1000X", "1500", "Samsung XYZ", "200");
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, autenticaUser);
        deleteProductRequest(SPEC, postProduct, getApiKey());
    }

    public String getApiKey() {
        return autenticaUser.getAuthToken();
    }

    @Test
    public void shouldReturnSuccessPostProductAndStatus201(){

        Response postProductSuccessResponse = registerProductRequestWithApiKey(SPEC, postProduct, getApiKey());
        postProductSuccessResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_INSERT_NEW_PRODUCT));
    }

    @Test
    public void shouldReturnFailureTokenInvalidMessagePostProductAndStatus401(){

        Response postProductSuccessResponse = registerProductRequestWithoutApiKey(SPEC, postProduct);
        postProductSuccessResponse.
                then().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_TOKEN_POST_PRODUCT));
    }
}
