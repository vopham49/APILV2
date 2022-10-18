package helper;

public class CommentHelper {


    public static String AddNewCommentBodyParam(String comment){
      String requestBody =  "{\n" +
                "    \"body\": {\n" +
                "        \"type\": \"doc\",\n" +
                "        \"version\": 1,\n" +
                "        \"content\": [\n" +
                "            {\n" +
                "                \"type\": \"paragraph\",\n" +
                "                \"content\": [\n" +
                "                    {\n" +
                "                        \"text\": \""+comment+"\",\n" +
                "                        \"type\": \"text\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
      return requestBody;
    }

}
