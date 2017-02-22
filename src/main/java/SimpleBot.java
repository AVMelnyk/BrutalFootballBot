import com.bfbot.DAO.MemeDAO;
import com.bfbot.entity.Meme;
import com.bfbot.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.*;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SimpleBot extends TelegramLongPollingBot {
    private String channelID = null;
    private String token = null;
    private long adminID = 163853091;
    static ArrayList<Meme> memesList = new ArrayList<Meme>();


    public static void main(String[] args) {


        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SimpleBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        MemeDAO memeDAO   =  new MemeDAO(session);
        List<Meme> memeList   = memeDAO.getAllMemes();
        for (Meme meme: memeList){
            new SimpleBot().postMemes(meme);
        }
        session.close();

    }

     public String getBotUsername() {
        return "BrutalFootballBot";
    }

    public String getChannelID() {
        if (channelID == null) {
            Map<String, String> env = System.getenv();
            channelID = env.get("CHANNEL_ID");
        }
        return channelID;

    }

    @Override
    public String getBotToken() {
        if (token == null) {
            Map<String, String> env = System.getenv();
            token = env.get("TELEGRAM_TOKEN");
        }
        return token;
    }
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText() && message.getChatId().equals(adminID)) {
        }
    }

    public  void postMemes(Meme meme){
        sendMeme(meme.getMemeText(),meme.getInputStream(meme.getLink()), meme.getMemeName());
    }
    private void sendMeme(String text,InputStream in,String name) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption(text);
        sendPhoto.setNewPhoto(name,in);
        sendPhoto.setChatId(getChannelID());
        try {
            sendPhoto(sendPhoto);
        }
        catch (org.telegram.telegrambots.exceptions.TelegramApiException e){
            e.printStackTrace();
        }

    }
}