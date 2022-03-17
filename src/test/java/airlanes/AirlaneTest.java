package airlanes;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class AirlaneTest {
	

	@BeforeTest()
	public void setup() {
		RestAssured.baseURI = "https://api.instantwebtools.net/";
		
}
	@Test(enabled =false)
	public void getAirlanes() {
		//RestAssured.baseURI = "https://api.instantwebtools.net/";
		
		Response response = given().log().all()
		.when().get("v1/airlines")
		.then().log().all().assertThat().statusCode(200).body("s.size()",greaterThan(0)).extract().response();
        //.then().log().all().assertThat().statusCode(200).body("name[0]", equalTo("Thai Airways")); 
//para acceder a los elementos del array  name 0 1 etc equal thai etc
		
		JsonPath json = new JsonPath(response.asString());
		System.out.println("Primer nombre de la respuesta: "  + json.getString("name[0]"));
		
		//validacion:
		
		AssertJUnit.assertEquals(json.get("name[0]"), "Thai Airways");
		
		
		//System.out.println("Respuesta GetAirlines: " + response.asString());
		
}
	@Test(enabled =false)
	public void getAirlines() {
		//RestAssured.baseURI = "https://api.instantwebtools.net/";
		
		given().pathParam("id", 5)
		.when().get("v1/airlines/{id}")
		.then().log().all().assertThat().statusCode(200)
		.header("Server", equalTo("ngnx/1.18.0"))
		.body("country", equalTo("Taiwan"));
	
	}
	@Test(enabled=false)
	public void createAirlane() {
		//RestAssured.baseURI = "https://api.instantwebtools.net/";
		
		given().header("Content-Type", "application/json").body("{\n"
				+ "\"id\": 60100000002,\n"
				+ "\"name\": \"Mijael's Airelines\",\n"
				+ "\"country\": \"Mexico\",\n"
				+ "\"logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/sri_lanka.png\",\n"
				+ "\"slogan\": \"Just do it\",\n"
				+ "\"head_quaters\": \"Katunayake, Sri Lanka\",\n"
				+ "\"website\": \"www.srilankaairways.com\",\n"
				+ "\"established\": \"1990\"\n"
				+ "}")
		.when().post("v1/airlines")
		.then().log().all().assertThat().statusCode(200).body("$", hasKey("_id"));
		
	}
	
		
		
		@Test()
		public void endToEnd() {
			Response response = given().header("Content-Type", "application/json").body("{\n"
					+ "    \"id\": 60100000005,\n"
					+ "    \"name\": \"Mijael's Airelines\",\n"
					+ "    \"country\": \"Mexico\",\n"
					+ "    \"logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/sri_lanka.png\",\n"
					+ "    \"slogan\": \"Just do it\",\n"
					+ "    \"head_quaters\": \"Katunayake, Sri Lanka\",\n"
					+ "    \"website\": \"www.srilankaairways.com\",\n"
					+ "    \"established\": \"1990\"\n"
					+ "}")
			.when().post("v1/airlines")
			.then().log().all().assertThat().statusCode(200).body("$", hasKey("_id")).extract().response();
			
			JsonPath json = new JsonPath(response.asString());
			
			Response responseGet = given().pathParam("id", json.getLong("id"))
								.when().get("v1/airlines/{id}")
								.then().log().all().assertThat()
									.statusCode(200)
									.header("Server", equalTo("nginx/1.18.0"))
									.body("name", equalTo("Mijael's Airelines")).extract().response();
			
		
			
			JsonPath jsonGet = new JsonPath(responseGet.asString());
			
			Assert.assertEquals(jsonGet.getString("country"), "Mexico");
			Assert.assertEquals(jsonGet.getString("logo"), "https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/sri_lanka.png");
			Assert.assertEquals(jsonGet.getString("slogan"), "Just do it");
		}
		
}

		