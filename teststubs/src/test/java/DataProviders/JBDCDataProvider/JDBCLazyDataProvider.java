package DataProviders.JBDCDataProvider;

import DataProviders.dataProvidersBase.DataCaster;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.Iterator;


public class JDBCLazyDataProvider extends DataCaster {

    private static String driverClass;
    private static String connectionString;
    private static String user;
    private static String password;

    private static String statementQuery;

    @DataProvider
    public Iterator<Object[]> jdbcLazyDataProvider(Method m) throws SQLException, ClassNotFoundException {

        if (!(m.isAnnotationPresent(JDBCDataSource.class))) {
            throw new Error("There is no @JDBCDataSource annotation on method " + m);
        }

        JDBCDataSource dataSource = m.getAnnotation(JDBCDataSource.class);
        driverClass = dataSource.driverClass();
        connectionString = dataSource.connectionURL();
        user = dataSource.user();
        password = dataSource.password();
        statementQuery = dataSource.statementQuery();

        return new TestData(m);
    }


    private static class TestData implements Iterator<Object[]> {
         private Method m;
         private Connection connection;
         private Statement statement;
         private ResultSet result;
         private boolean firstLine;

        public TestData(Method m) {
                this.m = m;
                firstLine = true;
            try {
                Class.forName(driverClass);

                if (user.equals("") && password.equals("")) {
                    connection = DriverManager.getConnection(connectionString);
                } else {
                    connection = DriverManager.getConnection(connectionString, user, password);
                }

                statement = connection.createStatement();

                result = statement.executeQuery(statementQuery);

            } catch (SQLException e) {
                closeConnection();
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                closeConnection();
                e.printStackTrace();
            }
        }


        public boolean hasNext() {

            try {

                if(firstLine) {
                    firstLine = false;
                    return true;
                }

                if(result.next()){
                    return true;
                }else{
                    closeConnection();
                    return false;
                }
            } catch (SQLException e) {
                closeConnection();
                return false;
            }

        }

        public Object[] next() {

            Object[] parameters = new Object[m.getParameterTypes().length];
            for(Object obj : parameters) obj = null;

            for(int i=0; i < m.getParameterTypes().length; i++ ){
                try {
                    parameters[i] = castDataType(m.getParameterTypes()[i], result.getObject(i+1));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return parameters;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }


        private void closeConnection(){

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException ignore) {
                }
        }
    }


}
