package com.olomsky.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.mongodb.MongoDBContainer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsApplicationTests {

    @ServiceConnection
	@Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.2").withReuse(true);

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
    }

    @Test
    void shouldCreateProduct() {
		Assertions.assertTrue(mongoDBContainer.isRunning());

        String requestBody = """
				{
                    "name": "Test product name",
                    "description": "Test product description",
                    "price": 112233
				}
				""";
		RestAssured.given().contentType("application/json").body(requestBody)
				   .when().post("/api/product").then().statusCode(201)
				   .body("id", Matchers.notNullValue())
				   .body("name", Matchers.equalTo("Test product name"))
				   .body("description", Matchers.equalTo("Test product description"))
				   .body("price", Matchers.equalTo(112233));
    }

}
