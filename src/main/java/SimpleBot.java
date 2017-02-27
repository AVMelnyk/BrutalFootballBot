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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SimpleBot extends TelegramLongPollingBot {
    private String channelID = null;
    private String token = null;
    private long adminID = 163853091;
    static ArrayList<Meme> memesList = new ArrayList<Meme>();


    public static void main(String[] args) throws IOException{
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SimpleBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        SimpleBot bot = new SimpleBot();
            MemeParser.putMemeInDB();
            Session session = HibernateUtil.getSessionFactory().openSession();
            MemeDAO memeDAO  =  new MemeDAO(session);
            List<Meme> memeList  = memeDAO.getAllUnpuplicedMemes();
            for (Meme meme: memeList){
                bot.sendMeme(meme);
                memeDAO.updateMemeStatus(meme);
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

    private void sendMeme(Meme meme) {

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption(meme.getMemeText());
        sendPhoto.setNewPhoto(meme.getMemeName(),meme.getInputStream(meme.getLink()));
        sendPhoto.setChatId(getChannelID());
        try {
            sendPhoto(sendPhoto);
        }
        catch (org.telegram.telegrambots.exceptions.TelegramApiException e){
            e.printStackTrace();
        }

    }
}