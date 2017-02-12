package com.bfbot.DAO;

import com.bfbot.entity.Meme;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class MemeDAO {
    private Session session;

    public MemeDAO(Session session) {
        this.session = session;
    }

    public void addMeme(Meme meme){
        Transaction transaction = session.beginTransaction();
        session.save(meme);
        transaction.commit();
        System.out.println("new Meme in your database "+meme.toString());
    }
    public Meme getMemeByID(int meme_id){
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM  Meme m WHERE m.meme_id="+meme_id );
        List <Meme>memeList = query.list();
        transaction.commit();
        Meme meme = memeList.get(0);
        System.out.println(meme);
        return meme;
    }
    public List<Meme> getAllMemes(){
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Meme ");
        List <Meme>memeList = query.list();
        return memeList;
    }
    public void updateMemeStatus(Meme meme){
        Transaction transaction = session.beginTransaction();
        Meme merged;
        Meme candidate =
                (Meme) session.get(Meme.class, meme.getMeme_id());
        if (candidate!= null){
            meme.setPubliced(true);
            merged = (Meme) session.merge(meme);
            session.save(merged);
        }
        transaction.commit();
        System.out.println("new Meme in your database"+meme.toString());
    }
    public void deleteMemeById(int meme_id){
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Meme meme =
                    (Meme) session.get(Meme.class, meme_id);
            session.delete(meme);
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        }
    }

}
