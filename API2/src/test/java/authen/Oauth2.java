package authen;

import config.BaseConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Oauth2 {

    @Test
    public static String getTokenKey(){
    String requestBody = "{\"grant_type\": \"client_credentials\",\n" +
            "\"client_id\": \""+ BaseConfig.CLIENT_ID +"\",\n" +
            "\"client_secret\": \""+ BaseConfig.CLIENT_SECRET +"\",\n" +
            "\"code\": \"read:write\",\n" +
            "\"redirect_uri\": \""+ BaseConfig.CALL_BACK_URL +"\"}";
    Response res = given().header("Content-Type","application/json").body(requestBody)
            .when().post("https://auth.atlassian.com/oauth/token").then().extract().response();

    return res.jsonPath().get("access_token");
    }

    public static String getCouldID(){
        Response res = given().header("Content-Type","application/json").auth().oauth2(getTokenKey())
                .when().get("https://api.atlassian.com/oauth/token/accessible-resources")
                .then().log().all().extract().response();

        return res.jsonPath().get("id[0]");
    }

}

