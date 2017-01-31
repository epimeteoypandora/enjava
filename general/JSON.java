
package general;

import com.google.gson.Gson;


public class JSON {
    public static String stringify(Object o){
        Gson gson = new Gson();  
        return gson.toJson(o);
    }
}
