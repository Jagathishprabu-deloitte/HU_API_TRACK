package mini_assignment_1;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class MiniAssignment1 {

    @Test
    public void get_test_call(){
        Response response =
        given().
                baseUri("https://jsonplaceholder.typicode.com").
        when().
                get("/posts").
        then().extract().response();
        assert (response.getStatusCode() == 200);
        assert (response.getContentType().contains("json"));
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
