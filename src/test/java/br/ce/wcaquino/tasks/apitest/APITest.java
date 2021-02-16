package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:18001";
		RestAssured.basePath = "/tasks-backend";
		
	}
	
	@Test
	public void deveRetornarSucesso() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);

	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		String body = "{\r\n" + 
				"	\"task\": \"Teste via API\",\r\n" + 
				"	\"dueDate\": \"2030-12-30\"\r\n" + 
				"}";
		RestAssured.given()
			.body(body)
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveAdicionarTarefaInvalida() {
		String body = "{\r\n" + 
				"	\"task\": \"Teste via API\",\r\n" + 
				"	\"dueDate\": \"2010-12-30\"\r\n" + 
				"}";
		RestAssured.given()
			.body(body)
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}

}
