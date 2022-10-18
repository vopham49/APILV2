import helper.CommentHelper;
import helper.IssueHelper;
import config.BaseConfig;
import config.CommentConfig;
import config.IssueConfig;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CommentTestCases extends BaseTest {
    public static String issueKey;


    @BeforeSuite(description = "Create new Issue")
    public void Precondition() {
        RestAssured.baseURI = BaseConfig.BASE_URL + couldID;
        String requestBody = IssueHelper.CreateNewIssueBodyParam(IssueConfig.ISSUE_SUMMARY,IssueConfig.ISSUE_TYPE_TASK, BaseConfig.PROJECT_ID);
        Response res =
                given().auth().oauth2(access_Token).contentType("application/json")
                        .body(requestBody)
                .when().post(IssueConfig.ISSUE_URL)
                .then().statusCode(201).extract().response();
       issueKey = res.jsonPath().get("key");
    }


    @Test(testName = "Verify that user can add new comment to the issue")
    public void AC_01(){
        log.info("Generate a random Comment string");
        String Comment =  "Comment_" + Double.toString(Math.random());
        log.info("Create the request json body");
        String requestBody = CommentHelper.AddNewCommentBodyParam(Comment);
        log.info("Send to request");
        Response res =
                given().auth().oauth2(access_Token).contentType("application/json")
                        .body(requestBody)
                .when()
                .post(CommentConfig.GenerateCreateCommentURL(issueKey));
        log.info("Verify the response status code is 201");
        Assert.assertEquals(201, res.getStatusCode());
        log.info("Verify message is contain :" + Comment);
        Assert.assertTrue(res.getBody().asString().contains(Comment));

}

    @Test(testName = "Verify that error message shown when user add new comment with a non- authorized account")
    public void AC_02(){
        log.info("Login with a non-authorized account");
       RestAssured.authentication =  RestAssured.preemptive().basic("vophamlogi555@gmail.com", "1gpC5537vkNfId444");
        log.info("Generate a random Comment string");
       String Comment =  "Comment_" + Double.toString(Math.random());
        log.info("Create the request json body");
       String requestBody = CommentHelper.AddNewCommentBodyParam(Comment);
        log.info("Send to request");
        Response res = given().contentType("application/json").body(requestBody)
                        .when()
                        .post(CommentConfig.GenerateCreateCommentURL(issueKey))
                        .then()
                        .extract().response();
        log.info("Verify that status code is 401");
        Assert.assertEquals(401, res.getStatusCode());
        log.info("Verify that error message is return correctly");
        Assert.assertEquals(res.jsonPath().get("message").toString(), BaseConfig.UNAUTHORIZED_ERROR_MSG);
    }

    @Test(testName = "Verify that user can't add new message with a empty message body")
    public void AC_03(){
        log.info("Create a empty Comment string");
        String Comment =  "";
        log.info("Create the request json body");
        String requestBody = CommentHelper.AddNewCommentBodyParam(Comment);
        log.info("Send to request");
        Response res = given().contentType("application/json").body(requestBody)
                        .auth().oauth2(access_Token).body(requestBody)
                        .when()
                        .post(CommentConfig.GenerateCreateCommentURL(issueKey))
                        .then().assertThat().statusCode(400)
                        .extract().response();
        log.info("Verify that status code is 400");
        Assert.assertEquals(400, res.getStatusCode());
        log.info("Verify that error message is return correctly");
        Assert.assertEquals(res.jsonPath().get("errors.comment").toString(),CommentConfig.COMMENT_BODY_EMPTY_ERROR_MSG);
    }

    @AfterSuite(description = "Deleting the issue")
    public void CleanUp() {
        Response res =
                given().auth().oauth2(access_Token)
                        .when().delete(IssueConfig.ISSUE_URL + "/" +issueKey)
                        .then().statusCode(204).log().all().extract().response();
    }
}
