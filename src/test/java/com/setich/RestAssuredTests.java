package com.setich;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests {
    String text = "To keep ReqRes free, contributions towards server costs are appreciated!";

    @BeforeAll
    static void setUrl() {
        baseURI = "https://reqres.in/";
    }

    @Test
    void singleTest() {
        given()
                .when()
                .get("/api/unknown/2")
                .then()
                .log().status()
                .log().body()
                .body("data.id", is(2))
                .body("data.name", is("fuchsia rose"))
                .body("data.year", is(2001))
                .body("data.color", is("#C74375"))
                .body("data.pantone_value", is("17-2031"))
                .body("support.url", is(baseURI + "#support-heading"))
                .body("support.text", is(text));
    }

    @Test
    void updateMethodTest() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .put("api/users/2")
                .then()
                .log().status()
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void unsuccessfulRegistrationTest() {
        String body = "{ \"email\": \"sydney@fife\" }";
        given()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void successfulRegistrationTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        given()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void listUsersTest() {
        given()
                .log().body()
                .when()
                .get("api/users?page=2")
                .then()
                .statusCode(200)
                .log().body()
                .body("page", is(2))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))

                .body("data.id[0]", is(7))
                .body("data.email[0]", is("michael.lawson@reqres.in"))
                .body("data.first_name[0]", is("Michael"))
                .body("data.last_name[0]", is("Lawson"))
                .body("data.avatar[0]", is(baseURI + "img/faces/7-image.jpg"))

                .body("data.id[1]", is(8))
                .body("data.email[1]", is("lindsay.ferguson@reqres.in"))
                .body("data.first_name[1]", is("Lindsay"))
                .body("data.last_name[1]", is("Ferguson"))
                .body("data.avatar[1]", is(baseURI + "img/faces/8-image.jpg"))

                .body("data.id[2]", is(9))
                .body("data.email[2]", is("tobias.funke@reqres.in"))
                .body("data.first_name[2]", is("Tobias"))
                .body("data.last_name[2]", is("Funke"))
                .body("data.avatar[2]", is(baseURI + "img/faces/9-image.jpg"))

                .body("data.id[3]", is(10))
                .body("data.email[3]", is("byron.fields@reqres.in"))
                .body("data.first_name[3]", is("Byron"))
                .body("data.last_name[3]", is("Fields"))
                .body("data.avatar[3]", is(baseURI + "img/faces/10-image.jpg"))

                .body("data.id[4]", is(11))
                .body("data.email[4]", is("george.edwards@reqres.in"))
                .body("data.first_name[4]", is("George"))
                .body("data.last_name[4]", is("Edwards"))
                .body("data.avatar[4]", is(baseURI + "img/faces/11-image.jpg"))

                .body("data.id[5]", is(12))
                .body("data.email[5]", is("rachel.howell@reqres.in"))
                .body("data.first_name[5]", is("Rachel"))
                .body("data.last_name[5]", is("Howell"))
                .body("data.avatar[5]", is(baseURI + "img/faces/12-image.jpg"))

                .body("support.url", is(baseURI + "#support-heading"))
                .body("support.text", is(text));

    }
}
