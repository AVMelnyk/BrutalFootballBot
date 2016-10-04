package test;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {
    public static void main(String[] args) {
        downloadMeme();
    }
    public static String readJSON(){
        String vkJSON = null;
        try {
            FileInputStream in = new FileInputStream("D:\\SimpleBot\\src\\test\\vk.json");
            byte[] buffer = new byte[in.available()];
            in.read(buffer,0,buffer.length);
            vkJSON = new String(buffer,"UTF-8");
        }
        catch (FileNotFoundException e){
            System.out.println("JSON file not founded");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return vkJSON;
    }
    public static void downloadMeme(){
        JSONObject obj = new JSONObject(readJSON());
        JSONArray arr = obj.getJSONArray("response");
        for (int i = 1; i < arr.length(); i++){
            try {
                String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_xxxbig");
                String[] parts = text.split("/");
                String path = "D:\\memasy\\"+parts[5];
                System.out.println(path);
                downloadFiles(text, path, 512);
            }
            catch (JSONException e) {
                try {
                    String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_xxbig");
                    String[] parts = text.split("/");
                    String path = "D:\\memasy\\"+parts[5];
                    System.out.println(path);
                    downloadFiles(text, path, 512);
                }
                catch (JSONException a){
                    try {
                        String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_xbig");
                        String[] parts = text.split("/");
                        String path = "D:\\memasy\\"+parts[5];
                        System.out.println(path);
                        downloadFiles(text, path, 512);
                    }
                    catch (JSONException b){
                        String text = arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString("src_big");
                        String[] parts = text.split("/");
                        String path = "D:\\memasy\\"+parts[5];
                        System.out.println(path);
                        downloadFiles(text, path, 512);
                    }
                }
            }
        }
    }
    public static void downloadFiles(String strURL, String strPath, int buffSize) {
        try {
            URL connection = new URL(strURL);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            InputStream in = null;
            in = urlconn.getInputStream();
            OutputStream writer = new FileOutputStream(strPath);
            byte buffer[] = new byte[buffSize];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
