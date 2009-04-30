
import java.sql.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:db/test;shutdown=true", "sa", "");
        try {
            conn.setAutoCommit(false);
            //
            Statement state = conn.createStatement();
            state.execute("create table test(id bigint,name varchar(100))");
            state.execute("insert into test(id,name) values(1,'test')");
            //
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}

