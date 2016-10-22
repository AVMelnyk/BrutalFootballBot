import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewParser {
    private static String query = "http://api.vk.com/method/wall.get?domain=fbrutal&count=5";
    public static void main(String[] args) {
        Parser.picSize.add("src_big");
        Parser.picSize.add("src_xbig");
        Parser.picSize.add("src_xxbig");
        Parser.picSize.add("src_xxxbig");
        printMeme();

    }

public static void printMeme(){
    JSONObject obj = new JSONObject(Parser.readJSON(Parser.getInputStream(NewParser.query)));
    JSONArray arr = obj.getJSONArray("response");
    for (int i = 2; i < 5; i++) {
        Meme meme = new Meme();
        meme.setMemeText(arr.getJSONObject(i).getString("text"));
        meme.setId(arr.getJSONObject(i).getInt("id"));
        meme.setDate(arr.getJSONObject(i).getInt("date"));
        meme.setLikes(arr.getJSONObject(i).getJSONObject("likes").getInt("count"));
        for (int j = 0; j < Parser.picSize.size(); j++) {
            try {
                meme.setLink(arr.getJSONObject(i).getJSONObject("attachment").getJSONObject("photo").getString(Parser.picSize.get(j)));
                String[] parts = meme.getLink().split("/");
                meme.setMemeName(parts[5]);
            } catch (JSONException e) {
            }
        }
        Meme.memeToString(meme);
    }

}
}
