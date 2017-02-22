package com.bfbot;


import com.bfbot.DAO.MemeDAO;
import com.bfbot.entity.Meme;
import com.bfbot.persistence.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class EnterPoint {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Meme meme = new Meme();
        meme.setMeme_id(1924314);
        meme.setLikes(335);
        meme.setLink("https://pp.vk.me/c7001/v7001102/2d721/_IgBFvOj-js.jpg");
        meme.setMemeName("IgBFvOj-js.jpg");
        meme.setPubliced(true);
        MemeDAO memeDAO  = new MemeDAO(session);
        List<Meme>memeList = memeDAO.getAllMemes();
        memeDAO.addMeme(meme);

        session.close();
    }
}
