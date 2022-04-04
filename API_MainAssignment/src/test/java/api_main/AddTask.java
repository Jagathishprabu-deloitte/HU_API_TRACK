package api_main;

import data.TaskData;
import data.UserData;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class AddTask {
    PrintStream logDetails = new PrintStream(new File("src\\Log.txt"));
    static ArrayList<String> list;
    static Response response;
    UserData userData = new UserData();
    TaskData taskData = new TaskData();

    public AddTask() throws IOException {

    }

    @Test(priority = 3)
    public void getTask() throws IOException {
        String cell = null;
        String taskFromExcel = null;
        String token = userData.getToken();
        System.out.println(token);
        list=new ArrayList<>();
        RestAssured.baseURI="https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request=RestAssured.given();
        request.header("Authorization","Bearer "+token).header("Content-Type","application/json").
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails));
        int lastRowNum = taskData.getLastRow();
        for (int i = 1; i <= lastRowNum; i++) {
            int lastCellNum = taskData.getLastCell(i);
            for (int j = 0; j < lastCellNum; j++) {
                cell = taskData.getUserTask(i,j);
                if (j == 0) {
                    taskFromExcel = cell;
                    JSONObject object = new JSONObject();
                    object.put("description", taskFromExcel);
                    request.body(object.toString()).post("/task");
                    list.add(taskFromExcel);
                    System.out.println(taskFromExcel+" ");
                }
            }
        }
        response=request.get("/task");
        System.out.println(response.prettyPrint());
    }

    @Test(priority = 4)
    public void validateTask() throws IOException {
        System.out.println("validation of tasks");
        System.out.println(response.prettyPrint());
        String owner_id= userData.getOwnerId();
        System.out.println(owner_id);
        JSONObject object=new JSONObject(response.asString());
        JSONArray arr=object.getJSONArray("data");
        for(int i=0;i<20;i++){
            String task= String.valueOf(arr.getJSONObject(i).get("description"));
            String owner= String.valueOf(arr.getJSONObject(i).get("owner"));
            Assert.assertEquals(task,list.get(i));
            Assert.assertEquals(owner,owner_id);
        }
        System.out.println("All the inputs are validated and it belongs to the user "+ userData.getName());
    }
    @Test(priority = 5)
    public void pageNationWithLimit2() throws IOException {
        String token = userData.getToken();
        System.out.println("Page nation with limit 2");
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = given();
        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json").
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails)).
                queryParam("limit",2).
                when().
                get("/task").
                then().
                statusCode(200).log().all();
    }
    @Test(priority = 6)
    public void pageNationWithLimit5() throws IOException {
        String token = userData.getToken();
        System.out.println("Page nation with limit 5");
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = given();
        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json").
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails)).
                queryParam("limit",5).
                when().
                get("/task").
                then().
                statusCode(200).log().all();
    }
    @Test(priority = 7)
    public void pageNationWithLimit10() throws IOException {
        String token = userData.getToken();
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = given();
        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json").
                filter(RequestLoggingFilter.logRequestTo(logDetails)).
                filter(ResponseLoggingFilter.logResponseTo(logDetails)).
                queryParam("limit",10).
                when().
                get("/task").
                then().
                statusCode(200).log().all();
    }
}
