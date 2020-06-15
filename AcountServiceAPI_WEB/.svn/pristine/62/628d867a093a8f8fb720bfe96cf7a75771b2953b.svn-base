package trong.lixco.com.util.rq;



import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
 
public class CommonService {
    private static final Gson gson=new Gson();

    public CommonService() {
    }
   public static String FormatResponse(int error, String msg) {
    if ((error == 0) && (msg.equals(""))) {
      msg = "No error";
    }
       JsonObject json = new JsonObject();
    json.addProperty("err", Integer.valueOf(error));
    json.addProperty("msg", msg);
    
    return gson.toJson(json);
  }
  
  public static String FormatResponse(int error, String msg, String data)
  {
    if ((error == 0) && (msg.equals(""))) {
      msg = "No error";
    }
    JsonObject json = new JsonObject();
    json.addProperty("err", Integer.valueOf(error));
    json.addProperty("msg", msg);
    json.addProperty("dt", data);
    
    return gson.toJson(json);
  }
  
  public static String FormatResponse(int error, String msg, Object objData)
  {
    if ((error == 0) && (msg.equals(""))) {
      msg = "No error";
    }
    
    JsonObject json = new JsonObject();
    json.addProperty("err", Integer.valueOf(error));
    json.addProperty("msg", msg);
    json.add("dt", gson.toJsonTree(objData));
    
    return gson.toJson(json);
  }
  
  public static String FormatResponse(int error, String msg, String objName, Object objData)
  {
    if ((error == 0) && (msg.equals(""))) {
      msg = "No error";
    }
    
    JsonObject json = new JsonObject();
    json.addProperty("err", Integer.valueOf(error));
    json.addProperty("msg", msg);
    JsonObject jsonParent = new JsonObject();
    jsonParent.add(objName, gson.toJsonTree(objData));
    json.add("dt", jsonParent);
    
    return gson.toJson(json);
  }
  



  public static String FormatResponse(String objName, Object objData)
  {
    JsonObject json = new JsonObject();
    JsonObject jsonParent = new JsonObject();
    jsonParent.add(objName, gson.toJsonTree(objData));
    

    return gson.toJson(jsonParent);
  }
  
  public static String FormatResponse(int error, String msg, JsonElement jsonEle) {
    if ((error == 0) && (msg.equals(""))) {
      msg = "No error";
    }
    JsonObject json = new JsonObject();
    json.addProperty("err", Integer.valueOf(error));
    json.addProperty("msg", msg);
    json.add("dt", jsonEle);
    return gson.toJson(json);
  }
}
