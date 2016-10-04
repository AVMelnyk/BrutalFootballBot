package test;


public class BotThread extends Thread{
    public void run() {
        System.out.println("Hello from a thread!");

    }

    public static void main(String args[]) {
        (new BotThread()).start();
    }

}
