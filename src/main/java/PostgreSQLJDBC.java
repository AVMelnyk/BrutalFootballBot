import java.sql.Connection;
import java.sql.DriverManager;
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
                        + "VALUES ("+meme.getId()
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
}
