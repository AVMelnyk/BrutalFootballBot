import com.bfbot.entity.Meme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgreSQLJDBC {
    public static void insertMemeIntoDB(Meme meme){
        {
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/MemesDB",
                                "postgres", "zk.,k.cfkj");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
                String sql = "INSERT INTO MEMES (ID,LINK,DATE,MEMENAME,MEMETEXT,PUBLICED,LIKES) "
                        + "VALUES ("+meme.getMeme_id()
                        +", '"+meme.getLink()+
                        "',"+ meme.getDate()+" ,'" +meme.getMemeName()+"', '"+meme.getMemeText()+"', false,"+meme.getLikes()+")";
                stmt.executeUpdate(sql);

                stmt.close();
                c.commit();
                c.close();
            } catch (Exception e) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
            System.out.println("Records created successfully");
        }
    }
    public static void selectMemeFromDB(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/MemesDB",
                            "postgres", "zk.,k.cfkj");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM MEMES ORDER BY ID DESC;" );
            while ( rs.next() ) {
                Meme meme = new Meme();

                meme.setMeme_id(rs.getInt("ID"));
                meme.setLink(rs.getString("LINK"));
                meme.setDate(rs.getInt("DATE"));
                meme.setMemeName(rs.getString("MEMENAME"));
                meme.setMemeText(rs.getString("MEMETEXT"));
                meme.setLikes(rs.getInt("LIKES"));
                //Meme.memeToString(meme);
                SimpleBot.memesList.add(meme);

            }
            for (Meme m: SimpleBot.memesList){
                System.out.println(m.getMeme_id());
            }
            rs.close();
            stmt.close();
            c.close();
            for (Meme meme: SimpleBot.memesList){
                meme.toString();
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

}
