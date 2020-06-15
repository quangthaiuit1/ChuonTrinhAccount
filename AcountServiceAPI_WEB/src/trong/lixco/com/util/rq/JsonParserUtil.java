package trong.lixco.com.util.rq;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class JsonParserUtil {

    public JsonParserUtil() {
    }
    private static final Gson gson=new Gson();
    public static Gson getGson(){
        return gson;
    }
    public static String parseStringValue(String data,String elementName){    
        JsonParser jsonParser=new JsonParser();
        JsonElement jsonEle=jsonParser.parse(data);
        return jsonEle.getAsJsonObject().get(elementName).getAsString();
                
    }
    public static String parseStringValue(String data,String objectName,String elementName){
        JsonParser jsonParser=new JsonParser();
        JsonElement jsonEle=jsonParser.parse(data);
        return jsonEle.getAsJsonObject().getAsJsonObject(objectName).get(elementName).getAsString();
        
    }
}
