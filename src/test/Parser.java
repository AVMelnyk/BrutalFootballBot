package test;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {
    public static void main(String[] args) {
        getMemeInputSteam();
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
    public static InputStream getMemeInputSteam(){
        JSONObject obj = new JSONObject(readJSON(getInputStream("http://api.vk.com/method/wall.get?domain=fbrutal&count=5")));
        JSONArray arr = obj.getJSONArray("response");
        InputStream meme = null;
        for (int i = 3; i < arr.length(); i++){
            try {
                String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_xxxbig");
                String[] parts = text.split("/");
                System.out.println(parts[5]);
                meme = getInputStream(text);
            }
            catch (JSONException e) {
                try {
                    String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_xxbig");
                    String[] parts = text.split("/");
                    System.out.println(parts[5]);
                    meme = getInputStream(text);
                }
                catch (JSONException a){
                    try {
                        String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_xbig");
                        String[] parts = text.split("/");
                        System.out.println(parts[5]);
                        meme = getInputStream(text);
                    }
                    catch (JSONException b){
                        String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_big");
                        String[] parts = text.split("/");
                        System.out.println(parts[5]);
                       meme=getInputStream(text);
                    }
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
