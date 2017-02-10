package DataProviders.JBDCDataProvider;


import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import java.sql.*;


/**
 * Created by sunny_IT on 2/10/2017.
 */
public class TestSample {

    @Test(enabled = true, dataProvider = "jdbcDataProvider", dataProviderClass = JDBCDataProvider.class)
    @JDBCDataSource(connectionURL = "jdbc:h2:~/test", user="sa", statementQuery ="Select * from USER" )
    public void testJDBCProviderSimple(int id, String name) {
        System.out.println("Hello, " + id + ": " + name);
    }


    @Test(enabled = true, dataProvider = "jdbcLazyDataProvider", dataProviderClass = JDBCLazyDataProvider.class)
    @JDBCDataSource(connectionURL = "jdbc:h2:~/test", user="sa", statementQuery ="Select * from USER" )
    public void testJDBCProviderLazy(int id, String name) {
        System.out.println("Hello, " + id + ": " + name);
    }


    @BeforeClass
    public void setup() throws ClassNotFoundException, SQLException {

        //just for DEMO

        Class.forName("org.h2.Driver");
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test;IFEXISTS=TRUE", "sa", "");
            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS USER;");
            statement.execute("CREATE TABLE USER(ID INT PRIMARY KEY, NAME VARCHAR(255));");

            statement.execute("INSERT INTO USER VALUES(1,'borya')");
            statement.execute("INSERT INTO USER VALUES(2,'petya')");
            statement.execute("INSERT INTO USER VALUES(3,'vasya')");
            ResultSet rs = statement.executeQuery("select * from user");
        }catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null)
                try { statement.close(); }
                catch (SQLException ignore) { }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException ignore) { }
        }

    }

}
