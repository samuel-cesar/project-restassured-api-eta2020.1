package models;
import org.json.simple.JSONObject;

public class User {

    public String name;
    public String email;
    public String password;
    public String isAdmin;
    public String authToken;
    public String userID;

    public User(String name, String email, String password, String isAdmin){
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public void setUserAuthToken(String authToken){
        this.authToken = authToken;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getUserCredentialsAsJson(){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("email",this.email);
        userJsonRepresentation.put("password",this.password);
        return userJsonRepresentation.toJSONString();
    }

//    public Response autenticateUser(){
//        Response loginResponse =
//                given().
//                        header("accept", "application/json").
//                        header("Content-Type", "application/json").
//                and().
//                        body(getUserCredentialsAsJson()).
//                when().post("login");
//
//        JsonPath jsonPathEvaluator = loginResponse.jsonPath();
//        String authToken = jsonPathEvaluator.get("authorization");
//        setUserAuthToken(authToken);
//
//        return loginResponse;
//    }

//    public Response registerUserRequest(){
//
//        JSONObject userJsonRepresentation = new JSONObject();
//        userJsonRepresentation.put("nome", this.name);
//        userJsonRepresentation.put("email", this.email);
//        userJsonRepresentation.put("password", this.password);
//        userJsonRepresentation.put("administrador", this.isAdmin);
//
//
//        Response response =
//               given().
//                       header("accept", "application/json").
//                       header("Content-Type", "application/json").
//               and().
//                       body(userJsonRepresentation.toJSONString()).
//               when().
//                       post("http://localhost:3000/usuarios");
//
//        return response;
//    }


}
