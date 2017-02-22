import com.bfbot.DAO.MemeDAO;
import com.bfbot.entity.Meme;
import com.bfbot.persistence.HibernateUtil;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MemeParser {

    public static void main(String[] args) throws IOException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        MemeDAO memeDAO   =  new MemeDAO(session);
        for (Meme meme: parseMeme()){
            //memeDAO.deleteMemeById(meme.getMeme_id());
            //memeDAO.addMeme(meme);
            memeDAO.updateMemeStatus(meme);
        }
        session.close();
    }

    public static List<Meme> parseMeme() throws IOException{
        List<Meme> memeList  = new ArrayList<Meme>();
        JSONObject jsonObject = readJsonFromUrl("https://api.vk.com/api.php?oauth=1&method=wall.get&domain=fbrutal&count=10");
        JSONArray array  = jsonObject.getJSONArray("response");
        for (int i = 1; i < array.length(); i++) {
            JSONObject object = (JSONObject) array.get(i);
            JSONArray attachments = object.getJSONArray("attachments");
            JSONObject attachmentsJSONObject = attachments.getJSONObject(0);
            String postType  = attachmentsJSONObject.get("type").toString();
            if (postType.equals("photo")){
                System.out.println("Фух, фото");
                int meme_id  = object.getInt("id");
                int meme_date  = object.getInt("date");
                JSONObject photo  = attachmentsJSONObject.getJSONObject("photo");
                String photoLink  = photo.getString("src_big");
                String[] parts = photoLink.split("/");
                String meme_name  = parts[6];
                String meme_text = array.getJSONObject(i).getString("text");
                int likes  = (object.getJSONObject("likes").getInt("count"));
                Meme meme = new Meme();
                meme.setMeme_id(meme_id);
                meme.setLink(photoLink);
                meme.setDate(meme_date);
                meme.setMemeName(meme_name);
                meme.setMemeText(meme_text);
                meme.setPubliced(false);
                meme.setLikes(likes);
                System.out.println(meme.toString());
                memeList.add(meme);
            }
            else if(postType.equals("video")){
                System.out.println("Блять, відео");
            }
        }
        return memeList;
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}