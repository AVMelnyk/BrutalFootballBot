public class Meme {
    private int id;
    private String link;
    private int date;
    private String memeName;
    private String memeText;
    private boolean publiced = false;
    private int likes;






    public boolean isPubliced() {
        return publiced;
    }
    public void setPubliced(boolean publiced) {
        this.publiced = publiced;
    }
    public static void main(String[] args) {
        System.out.println();
    }
    public String getMemeText() {
        return memeText;
    }
    public void setMemeText(String memeText) {
        this.memeText = memeText;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMemeName() {
        return memeName;
    }
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setMemeName(String memeName) {
        this.memeName = memeName;
    }
    public static void memeToString(Meme meme){
        System.out.println("Name: " + meme.getMemeName()+"\n"+"ID: "+meme.getId()+"\n"+"Date: "+meme.getDate()+"\n"+
                        "Text: "+meme.getMemeText()+"\n"+"Link: "+meme.getLink()+"\n"+"Likes: "+meme.getLikes()+"\n");
    }

}
