package api_main;

import data.UserData;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class RegisterAndLoginUser {
    String name, email, password;
    int age;
    UserData userData = new UserData();
    PrintStream logDetails = new PrintStream(new File("src\\Log.txt"));
    public RegisterAndLoginUser() throws IOException {

    }

    @Test(priority = 1)
    public void userRegister() throws IOException {
        JSONObject object = new JSONObject();
        name = userData.getName();
        email = userData.getEmail();
        password = userData.getPassword();
        age = userData.getAge();
        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
        System.out.println(age);
        object.put("name", name);
        object.put("email", email);
        object.put("password", password);
        object.put("age", age);
        given().baseUri("https://api-nodejs-todolist.herokuapp.com").
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails)).
                header("Content-Type", "application/json").
                body(object.toString()).
                when().post("/user/register").
                then().statusCode(201).extract().response();
    }

    @Test(priority = 2)
    public void userRegisterNegative() throws IOException {
        JSONObject object = new JSONObject();
        name = userData.getName();
        email = userData.getEmail();
        password = userData.getPassword();
        age = userData.getAge();
        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
        System.out.println(age);
        object.put("name", name);
        object.put("email", email);
        object.put("password", password);
        object.put("age", age);
        given().baseUri("https://api-nodejs-todolist.herokuapp.com").
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails)).
                header("Content-Type", "application/json").
                body(object.toString()).
                when().post("/user/register").
                then().statusCode(201).extract().response();
    }



    @Test(priority = 2)
    public void userLogin() throws IOException {
        JSONObject object = new JSONObject();
        email = userData.getEmail();
        password = userData.getPassword();
        object.put("email", email);
        object.put("password", password);
        System.out.println(email);
        System.out.println(password);
        Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com").
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails)).
                header("Content-Type", "application/json").
                body(object.toString()).
                when().post("/user/login").
                then().statusCode(200).extract().response();
                String jsonString= response.getBody().asString();
                String tokenGenerated = JsonPath.from(jsonString).get("token");
                userData.writeToken(tokenGenerated);
                System.out.println(tokenGenerated);
        String jsonId= response.getBody().asString();
        String userId = JsonPath.from(jsonId).get("user._id");
        userData.writeOwnerId(userId);
        System.out.println(userId);
    }

    @Test(priority = 2)
    public void userLoginNegative() {
        JSONObject loginObject = new JSONObject();
        loginObject.put("email", "at@gmail.com");
        loginObject.put("password", "A1234556");
        Response response = given().spec(requestSpecification).
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails)).
                body(loginObject.toString()).
                when().post("/user/login").
                then().statusCode(200).extract().response();
    }

}