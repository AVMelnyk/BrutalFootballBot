import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewParser {
    private static String query = "http://api.vk.com/method/wall.get?domain=fbrutal&count=5";
    public static void main(String[] args) {
        MemParser.picSize.add("src_big");
        MemParser.picSize.add("src_xbig");
        MemParser.picSize.add("src_xxbig");
        MemParser.picSize.add("src_xxxbig");
        printMeme();

    }

public static void printMeme(){
    JSONObject obj = new JSONObject(MemParser.readJSON(MemParser.getInputStream(NewParser.query)));
    JSONArray arr = obj.getJSONArray("response");
    for (int i = 2; i < 5; i++) {
        Meme meme = new Meme();
        meme.setMemeText(arr.getJSONObject(i).getString("text"));
        meme.setId(arr.getJSONObject(i).getInt("id"));
        meme.setDate(arr.getJSONObject(i).getInt("date"));
        meme.setLikes(arr.getJSONObject(i).getJSONObject("likes").getInt("count"));
        for (int j = 0; j < MemParser.picSize.size(); j++) {
            try {
                meme.setLink(arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString(MemParser.picSize.get(j)));
                String[] parts = meme.getLink().split("/") ;
                meme.setMemeName(parts[5]);
            } catch (JSONException e) {
            }
        }
        Meme.memeToString(meme);
        PostgreSQLJDBC.insertMemeIntoDB(meme);
    }

}
}
