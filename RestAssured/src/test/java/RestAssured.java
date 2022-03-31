import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestAssured {

    @Test
    public void get_test_call(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type","application/json").
        when().
                get("/posts").
        then().
                statusCode(200).
                body("id[39]",equalTo(40)).
                body("userId[39]",equalTo(4)).
                body(containsString("title"));
    }

    @Test
    public void put_test_call(){
        File jsonData = new File("src\\test\\resource\\putdata.json");
        given().
                baseUri("https://reqres.in/api").
                body(jsonData).
                header("Content-Type","application/json").
        when().
                put("/users").
        then().
                statusCode(200).
                body("name",equalTo("Arun")).
                body("job",equalTo("Manager"));

    }

}
