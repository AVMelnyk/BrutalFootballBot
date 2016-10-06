package test;


public class BotThread extends Thread{
    public void run() {
        System.out.println("Hello from a thread!");
        //new SimpleBot().uploadFile(Parser.getMemeInputSteam(),"Meme", )
    }

    public static void main(String args[]) {
        (new BotThread()).start();
    }

}
