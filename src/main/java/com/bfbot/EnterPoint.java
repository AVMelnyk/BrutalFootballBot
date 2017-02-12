package com.bfbot;


import com.bfbot.DAO.MemeDAO;
import com.bfbot.entity.Meme;
import com.bfbot.persistence.HibernateUtil;
import org.hibernate.Session;

public class EnterPoint {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Meme meme = new Meme();
        meme.setLink("fd");
        meme.setMeme_id(124);
        meme.setLikes(323);
        //set
        MemeDAO memeDAO  = new MemeDAO(session);
        //memeDAO.addMeme(meme);
        System.out.println(memeDAO.getMemeByID(124).toString());
        //session.save(meme);
        /*List<Meme> newlist = memeDAO.getAllMemes();
        for (Meme m: newlist
             ) {
            System.out.println(m.toString());
        }*/
        //memeDAO.updateMemeStatus(meme);
        memeDAO.deleteMemeById(124);
        session.close();
    }
}
