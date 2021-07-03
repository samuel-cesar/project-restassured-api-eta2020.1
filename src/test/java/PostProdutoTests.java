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

    private Product sameProduct1;
    private Product sameProduct2;

    @BeforeClass
    public void generateTestData(){

        //Usuario que gera a apiKey para realizar o POST PRODUCT
        autenticaUser = new User("Samuel Autenticar Com ApiKey", "samuel_autentica@email.com", "123", "true");
        registerUserRequest(SPEC, autenticaUser);

        Response loginSuccessResponse = authenticateUserRequest(SPEC, autenticaUser);
        loginSuccessResponse.
                then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_LOGIN)).
                body("authorization", notNullValue());

        //Produto inserido no POST PRODUCT
        postProduct = new Product("Produto PostProduct 1000X", "1500", "Samsung XYZ", "200");

        //Massa para o teste status code 400 'Já existe produto com esse nome'
        sameProduct1 = new Product("Teste de produto com o mesmo nome", "1500", "Samsung XYZ", "200");
        registerProductRequestWithApiKey(SPEC, sameProduct1, getApiKey());

        //Massa para o teste status code 400 em que o produto com o mesmo nome ja cadastradao será inserido no próprio teste
        sameProduct2 = new Product("Teste de produto com o mesmo nome", "1600", "Samsung ABC", "400");
    }

    //Deleção das massas após o teste
    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, autenticaUser);
        deleteProductRequest(SPEC, postProduct, getApiKey());
        deleteProductRequest(SPEC, sameProduct1, getApiKey());
    }

    //getApiKey para utilizar nos cenários que precisam de Authorization
    public String getApiKey() {
        return autenticaUser.getAuthToken();
    }

    //Cadastro com sucesso Status Code 201
    @Test
    public void shouldReturnSuccessPostProductAndStatus201(){

        Response postProductSuccessResponse = registerProductRequestWithApiKey(SPEC, postProduct, getApiKey());
        postProductSuccessResponse.
                then().
                log().all().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_INSERT_NEW_PRODUCT));
    }

    //Token ausente, inválido ou expirado Status Code 401
    @Test
    public void shouldReturnFailureTokenInvalidMessagePostProductAndStatus401(){

        Response postProductSuccessResponse = registerProductRequestWithoutApiKey(SPEC, postProduct);
        postProductSuccessResponse.
                then().
                log().all().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_TOKEN_POST_PRODUCT));
    }

    //Já existe produto com esse nome Status Code 400
    @Test
    public void shouldReturnFailureMessageRegisterProductSameNameAndStatus400(){

        Response postProductSuccessResponse = registerProductRequestWithApiKey(SPEC, sameProduct2, getApiKey());
        postProductSuccessResponse.
                then().
                log().all().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_FAILED_INSERT_NEW_PRODUCT_WITH_THE_SAME_NAME));
    }
}
