package test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.*;

public class SimpleBot extends TelegramLongPollingBot {
    public String channelID = "-1001071572976";

    public static void main(String[] args) {
        Parser.picSize.add("src_big");
        Parser.picSize.add("src_xbig");
        Parser.picSize.add("src_xxbig");
        Parser.picSize.add("src_xxxbig");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SimpleBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "BrutalFootballBot";
    }

    @Override
    public String getBotToken() {
        String token = null;
        try {
            FileInputStream in = new FileInputStream("D:\\SimpleBot\\src\\test\\token.txt");
            byte[] buffer = new byte[in.available()];
            in.read(buffer,0,buffer.length);
            token = new String(buffer,"UTF-8");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
                while (true){
                    try {
                        Parser.getMemeInputSteam();
                        if (Parser.lastParseMemeTime>Parser.lastSendMemeTime){
                            uploadFile(Parser.getMemeInputSteam(), Parser.fileName, channelID);
                            sendMsg(channelID, Parser.message);
                            Thread.sleep(60000);
                        }
                        else{
                            Thread.sleep(60000);
                            System.out.println("Нових мемів немає");
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    catch (java.lang.InterruptedException e){
                        e.printStackTrace();
                    }
                    Parser.lastSendMemeTime = Parser.lastParseMemeTime;
                }

        }
    }
    public HttpEntity uploadFile (InputStream f, String fileName, String chat_id)throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("https://api.telegram.org/bot"+getBotToken()+"/sendPhoto");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("chat_id", chat_id, ContentType.TEXT_PLAIN);

// This attaches the file to the POST:
        builder.addBinaryBody(
                "photo",
                f,
                ContentType.APPLICATION_OCTET_STREAM,
                fileName
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        String result;
        if (responseEntity != null) {
            // A Simple JSON Response Read
            InputStream instream = responseEntity.getContent();
            result = convertStreamToString(instream);
            // now you have the string representation of the HTML request
            System.out.println("RESPONSE: " + result);
            instream.close();
        }
        return responseEntity;
    }
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    private void sendMsg(String ID, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(ID);
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}