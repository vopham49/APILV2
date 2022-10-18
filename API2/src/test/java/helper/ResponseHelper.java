package helper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class ResponseHelper {

    public static String Get_Object_Element_Info(Response response, String object,String element, Integer index) {
        Map<String, List<Map<String, String>>> projectInfo = JsonPath.from(response.asString()).get();
        List<Map<String, String>> elementList = projectInfo.get(object);
        System.out.println(elementList);
       // String returnValue = String.valueOf(elementList.get(element));
        return elementList.toString();
    }

}