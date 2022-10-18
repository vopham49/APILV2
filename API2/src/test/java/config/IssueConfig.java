package config;

import org.apache.poi.ss.formula.functions.Today;

public class IssueConfig {
     public static final String ISSUE_URL =  "/rest/api/3/issue";
     public static final String ISSUE_SUMMARY = "Issue_" + Double.toString(Math.random());
     public static final Integer ISSUE_TYPE_BUG = 10003;
     public static final Integer ISSUE_TYPE_STORY = 10001;
     public static final Integer ISSUE_TYPE_TASK = 10002;
     public static final String NO_ISSUE_SUMMARY_ERROR_MSG = "You must specify a summary of the issue.";
}
