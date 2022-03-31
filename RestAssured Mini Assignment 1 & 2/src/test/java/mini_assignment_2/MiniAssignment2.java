package mini_assignment_2;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class MiniAssignment2 {

    RequestSpecification requestSpecification1;
    ResponseSpecification responseSpecification1;

    RequestSpecification requestSpecification2;
    ResponseSpecification responseSpecification2;

    @BeforeClass
    public void setup(){

        RequestSpecBuilder requestSpecBuilder1 = new RequestSpecBuilder();
        requestSpecBuilder1.setBaseUri("https://jsonplaceholder.typicode.com").
                addHeader("Content-Type","application/json");

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri("https://reqres.in/api").
                addHeader("Content-Type","application/json");

        requestSpecification1 = RestAssured.with().spec(requestSpecBuilder1.build());
        requestSpecification2 = RestAssured.with().spec(requestSpecBuilder2.build());

        responseSpecification1 = RestAssured.expect().contentType(ContentType.JSON).statusCode(200);
        responseSpecification2 = RestAssured.expect().contentType(ContentType.JSON).statusCode(200);
    }

    @Test
    public void get_test_call(){
        Response response =
                given().
                        spec(requestSpecification1).
                when().
                        get("/posts").
                then().spec(responseSpecification2).extract().response();
        JSONArray arr = new JSONArray(response.asString());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            if(obj.getInt("id")==40){
                assert(obj.getInt("userId")==4);
            }
            assertThat(obj.getString("title"),true);
        }
    }

    @Test
    public void put_test_call(){
        File jsonData = new File("src\\test\\resource\\putdata.json");
        given().
                spec(requestSpecification2).
                body(jsonData).
        when().
                put("/users").
        then().
                spec(responseSpecification2).
                body("name",equalTo("Arun")).
                body("job",equalTo("Manager"));

    }

}
