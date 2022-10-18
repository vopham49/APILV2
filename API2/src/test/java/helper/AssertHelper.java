package helper;

import org.testng.Assert;

public class AssertHelper {

    public static void verifyStaticCode(Integer actual, Integer expected){

        Assert.assertEquals(actual,expected);
    }
}
