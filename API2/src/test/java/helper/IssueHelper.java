package helper;


import authen.Oauth2;
import config.BaseConfig;
import config.IssueConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IssueHelper {

    public static String CreateNewIssueBodyParam(String issueSummary, Integer issueTypeID, Integer projectID){
        String requestBody = "{\n" +
                "    \"fields\": {\n" +
                "    \"summary\": \""+ issueSummary +"\",\n" +
                "    \"issuetype\": {\n" +
                "      \"id\": \""+issueTypeID+"\"\n" +
                "    },\n" +
                "    \"project\": {\n" +
                "      \"id\": \""+projectID+"\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        return requestBody;
    }

    public static String GetIssueSummaryWithIssueKey(String issueKey){
         String access_Token = Oauth2.getTokenKey();
         String couldID = Oauth2.getCouldID();
        RestAssured.baseURI = BaseConfig.BASE_URL + couldID;
         Response res =
                given().auth().oauth2(access_Token).contentType("application/json")
                        .when().get(IssueConfig.ISSUE_URL+"/" + issueKey)
                        .then().extract().response();
         String issueSummary = res.jsonPath().get("fields.summary");
         return issueSummary;
     }

}
