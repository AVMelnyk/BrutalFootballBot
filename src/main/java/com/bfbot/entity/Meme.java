package com.bfbot.entity;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
@Entity
@Table(name = ("memestable"))
public class Meme {


    @Id
    @Column(name = "MEME_ID")
    private Integer meme_id;

    @Column(name = "LINK")
    private String link;

    @Column(name = "DATE")
    private int date;

    @Column(name = "MEME_NAME")
    private String memeName;

    @Column(name = "MEME_TEXT")
    private String memeText;

    @Column(name = "PUBLICED")
    private boolean publiced = false;

    @Column(name = "LIKES")
    private int likes;


    public Integer getMeme_id() {
        return meme_id;
    }

    public void setMeme_id(Integer meme_id) {
        this.meme_id = meme_id;
    }
    public boolean isPubliced() {
        return publiced;
    }
    public void setPubliced(boolean publiced) {
        this.publiced = publiced;
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

    @Override
    public  String toString(){
        return "Name: " + memeName+"\n"+"ID: "+meme_id+"\n"+"Date: "+date+"\n"+
                        "Text: "+memeText+"\n"+"Link: "+link+"\n"+"Likes: "+likes+"\n";

    }
    public  InputStream getInputStream(String strURL) {
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
