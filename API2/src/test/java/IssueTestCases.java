import config.BaseConfig;
import config.CommentConfig;
import config.IssueConfig;
import helper.CommentHelper;
import helper.IssueHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class IssueTestCases extends BaseTest{

    @Test(testName = "Verify that user can create new issue")
    public void CI_01(){
        log.info("Generate a issue summary");
        String issueSummary = IssueConfig.ISSUE_SUMMARY;
        log.info("Generate request body");
        RestAssured.baseURI = BaseConfig.BASE_URL + couldID;
        String requestBody = IssueHelper.CreateNewIssueBodyParam(issueSummary,IssueConfig.ISSUE_TYPE_TASK, BaseConfig.PROJECT_ID);
        log.info("send the request");
        Response res =
                given().auth().oauth2(access_Token).contentType("application/json")
                        .body(requestBody)
                        .when().post(IssueConfig.ISSUE_URL)
                        .then().extract().response();
        log.info("Get Issue Summary");
        String issue_Key = res.jsonPath().get("key");
        String expectedIssueSummary =  IssueHelper.GetIssueSummaryWithIssueKey(issue_Key);
        log.info("Verify the response status code is 201");
        Assert.assertEquals(201, res.getStatusCode());
        log.info("Verify issue "+ issueSummary+ " is created");
        Assert.assertEquals(expectedIssueSummary, issueSummary);
    }

    @Test(testName = "Verify that user can create new issue")
    public void CI_02(){
        log.info("Login with a non-authorized account");
        RestAssured.authentication =  RestAssured.preemptive().basic("vophamlogi555@gmail.com", "1gpC5537vkNfId444");
        log.info("Generate a issue summary");
        String issueSummary = IssueConfig.ISSUE_SUMMARY;
        RestAssured.baseURI = BaseConfig.BASE_URL + couldID;
        log.info("send the request");
        String requestBody = IssueHelper.CreateNewIssueBodyParam(issueSummary,IssueConfig.ISSUE_TYPE_TASK, BaseConfig.PROJECT_ID);
        Response res =
                given().contentType("application/json")
                        .body(requestBody)
                        .when().get(IssueConfig.ISSUE_URL)
                        .then().extract().response();
        log.info("Verify the response status code is 401");
        Assert.assertEquals(401, res.getStatusCode());
        log.info("Verify that error message is return correctly");
        Assert.assertEquals(res.jsonPath().get("message").toString(), BaseConfig.UNAUTHORIZED_ERROR_MSG);
    }

    @Test(testName = "Verify that user can't add new message with a empty message body")
    public void AC_03(){
        log.info("Generate a issue summary");
        String issueSummary = "";
        log.info("Generate request body");
        RestAssured.baseURI = BaseConfig.BASE_URL + couldID;
        String requestBody = IssueHelper.CreateNewIssueBodyParam(issueSummary,IssueConfig.ISSUE_TYPE_TASK, BaseConfig.PROJECT_ID);
        log.info("send the request");
        Response res =
                given().auth().oauth2(access_Token).contentType("application/json")
                        .body(requestBody)
                        .when().post(IssueConfig.ISSUE_URL)
                        .then().extract().response();
        log.info("Verify that status code is 400");
        Assert.assertEquals(400, res.getStatusCode());
        log.info("Verify that error message is return correctly");
        Assert.assertEquals(res.jsonPath().get("errors.summary").toString(),IssueConfig.NO_ISSUE_SUMMARY_ERROR_MSG);
    }

}
