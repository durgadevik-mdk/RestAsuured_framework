package testcases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class RestAssured_API {

	public void get_all_products_list() {

		Response response = given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json;charset=UTF-8")
				.when()
				.get("/read.php")
				.then()
				.extract().response();

		int statuscode = response.getStatusCode();
		System.out.println("StatusCode : " + statuscode);
		// System.out.println("Response API " +response.print());

		String responsebody = response.getBody().asString();
		System.out.println(responsebody);

	}

	public void get_a_product_from_list() {

		Response response = given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json;charset=UTF-8")
				.queryParam("id", "1981")
				.when()
				.get("/read_one.php")
				.then()
				.extract().response();

		int statuscode = response.getStatusCode();
		System.out.println("StatusCode : " + statuscode);

		String responsebody = response.getBody().prettyPrint();
		// System.out.println(responsebody);

		long responsetime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responsetime);
		if (responsetime <= 2000) {
			System.out.println("Responsetime is within range");
		} else {
			System.out.println("Responsetime is out of range");

		}

		JsonPath jsp = new JsonPath(responsebody);
		String Response_product_name = jsp.getString("name");
		System.out.println(Response_product_name);
		Assert.assertEquals("Amazing Pillow 2.0", Response_product_name);

		String Response_product_description = jsp.getString("description");
		System.out.println(Response_product_description);
		Assert.assertEquals("The best pillow for amazing programmers.", Response_product_description);

		String Response_product_price = jsp.getString("price");
		System.out.println(Response_product_price);
		Assert.assertEquals("199", Response_product_price);

	}

	public void create_a_product() {
		
		String createpayload = "src/main/java/createpayloadbody.json";

		/*
		 * HashMap<String, String> bodypayload = new HashMap<String, String>();
		 * bodypayload.put("name", "Welcome 2nd once again"); bodypayload.put("price",
		 * "299929"); bodypayload.put("description", "The best movie ever ");
		 * bodypayload.put("category_id", "6"); bodypayload.put("category_name",
		 * "Books");
		 */

		Response response = 
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type", "application/json")
					.body(new File(createpayload))
				.when()
					.log().all()
					.post("/create.php")
				.then()
					.log().all()
					.extract().response();

		int statuscode = response.getStatusCode();
		System.out.println("StatusCode : " + statuscode);
		Assert.assertEquals(201, statuscode);
		// System.out.println("Response API " +response.print());

		String responsebody = response.getBody().asString();// response prints in json format so spliting the response
															// to validate individual value
		System.out.println(responsebody);

		JsonPath jsp = new JsonPath(responsebody);
		String responsemessgae = jsp.getString("message");
		System.out.println(responsemessgae);
		Assert.assertEquals("Product was created.", responsemessgae);

	}

	
	public void update_a_product() {

		String updatebodypayload = "src/main/java/updatepayloadbody.json";
		/*
		 * HashMap<String,String> updatebodypayload = new HashMap<String,String>();
		 * updatebodypayload.put("id", "2079"); updatebodypayload.put("name",
		 * "Welcome"); updatebodypayload.put("price","299.99");
		 * updatebodypayload.put("description","The best movie");
		 * updatebodypayload.put("category_id","6");
		 * updatebodypayload.put("category_name","Books");
		 */

		Response response =
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type", "application/json")
					.body(new File(updatebodypayload))
				.when()
					.log().all()
					.put("/update.php")
				.then()
					.log().all()
					.extract().response();

		int statuscode = response.getStatusCode();
		System.out.println("StatusCode : " + statuscode);
		Assert.assertEquals(200, statuscode);
		// System.out.println("Response API " +response.print());

		String responsebody = response.getBody().asString();// response prints in json format so spliting the response
															// to validate individual value
		System.out.println(responsebody);

		JsonPath jsp = new JsonPath(responsebody);
		String responsemessgae = jsp.getString("message");
		System.out.println(responsemessgae);
		Assert.assertEquals("Product was updated.", responsemessgae);

	}

	@Test
public void delete_a_product() {
		
		String deletepayload = "src/main/java/deletepayload.json";

		Response response = 
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type", "application/json")
					.body(new File(deletepayload))
				.when()
					.log().all()
					.delete("/delete.php")
				.then()
					.log().all()
					.extract().response();

		int statuscode = response.getStatusCode();
		System.out.println("StatusCode : " + statuscode);
		Assert.assertEquals(200, statuscode);
		// System.out.println("Response API " +response.print());

		String responsebody = response.getBody().asString();// response prints in json format so spliting the response
															// to validate individual value
		System.out.println(responsebody);

		JsonPath jsp = new JsonPath(responsebody);
		String responsemessgae = jsp.getString("message");
		System.out.println(responsemessgae);
		Assert.assertEquals("Product was deleted.", responsemessgae);

	}

}
