import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.Map;

public class SimpleBot extends TelegramLongPollingBot {
    private String channelID = null;
    private String token = null;
    private long adminID = 163853091;


    public static void main(String[] args) {
        MemParser.picSize.add("src_big");
        MemParser.picSize.add("src_xbig");
        MemParser.picSize.add("src_xxbig");
        MemParser.picSize.add("src_xxxbig");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SimpleBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
            while (true) {
                try {
                    MemParser.getMemeInputSteam();
                    if (MemParser.lastParseMemeTime > MemParser.lastSendMemeTime) {
                        sendPht(MemParser.message,MemParser.getMemeInputSteam());
                        Thread.sleep(60000 * 3);
                    } else {
                        Thread.sleep(60000 * 3);
                        System.out.println("Нових мемів немає");
                    }
                }  catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MemParser.lastSendMemeTime = MemParser.lastParseMemeTime;
            }

        }
    }
    private void sendPht(String text,InputStream in) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption(text);
        sendPhoto.setNewPhoto("a.jpg",in);
        sendPhoto.setChatId(getChannelID());
        try {
            sendPhoto(sendPhoto);
        }
        catch (org.telegram.telegrambots.exceptions.TelegramApiException e){
            e.printStackTrace();
        }

    }

}