import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Parser {
    public static String message = null;
    public static int lastSendMemeTime = 0;
    public static int lastParseMemeTime = 0;
    public static ArrayList<String> picSize = new ArrayList<String>();
    public static String fileName = null;
    public static void main(String[] args) {
    }
    public static String readJSON(InputStream inputStream){
        String vkJSON = null;
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer,0,buffer.length);
            vkJSON = new String(buffer,"UTF-8");
        }
        catch (FileNotFoundException e){
            System.out.println("JSON file not found");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return vkJSON;
    }
    public static InputStream getMemeInputSteam() {
        JSONObject obj = new JSONObject(readJSON(getInputStream("http://api.vk.com/method/wall.get?domain=fbrutal&count=2")));
        JSONArray arr = obj.getJSONArray("response");
        InputStream meme = null;
        for (int i = 1; i < arr.length(); i++) {
            lastParseMemeTime = arr.getJSONObject(i).getInt("date");
            for (int j = 0; j < picSize.size(); j++) {
                try {
                    message = arr.getJSONObject(i).getString("text");
                    String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString(picSize.get(j));
                    meme = getInputStream(text);
                    String[] parts = text.split("/");
                    fileName = parts[5];
                } catch (JSONException e) {
                    System.out.println("Інший розмір");
                }
            }
        }
        return meme;
    }
    public static InputStream getInputStream(String strURL) {
        InputStream input = null;
        try {
            URL connection = new URL(strURL);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            input = urlconn.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
        }
        return input;
    }
}
