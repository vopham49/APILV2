package config;

public class CommentConfig {

    public static String GenerateCreateCommentURL(String issueKey){
        String createCommentURL = "/rest/api/3/issue/"+ issueKey +"/comment";
        return createCommentURL;
    };

    public static final String COMMENT_BODY_EMPTY_ERROR_MSG = "Comment body can not be empty!";
}
