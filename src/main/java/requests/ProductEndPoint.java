package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static requests.UserEndpoint.authenticateUserRequest;

public class ProductEndPoint extends RequestBase{

    public static Response registerProductRequestWithApiKey(RequestSpecification spec, Product product, String apiKey){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome", product.nome);
        userJsonRepresentation.put("preco",product.preco);
        userJsonRepresentation.put("descricao", product.descricao);
        userJsonRepresentation.put("quantidade",product.quantidade);

        Response registerProductResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        header("Authorization", apiKey ).
                        and().
                        body(userJsonRepresentation.toJSONString()).
                        when().
                        post("produtos");

        product.setProductID(getValueFromResponse(registerProductResponse, "_id"));
        return registerProductResponse;
    }

    public static Response registerProductRequestWithoutApiKey(RequestSpecification spec, Product product){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome", product.nome);
        userJsonRepresentation.put("preco",product.preco);
        userJsonRepresentation.put("descricao", product.descricao);
        userJsonRepresentation.put("quantidade",product.quantidade);

        Response registerProductResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        and().
                        body(userJsonRepresentation.toJSONString()).
                        when().
                        post("produtos");

        product.setProductID(getValueFromResponse(registerProductResponse, "_id"));
        return registerProductResponse;
    }

    public static Response deleteProductRequest(RequestSpecification spec, Product product, String apiKey){

        Response deleteProductResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        header("Authorization", apiKey ).
                        when().
                        delete("produtos/"+product.productID);
        return deleteProductResponse;
    }
}
