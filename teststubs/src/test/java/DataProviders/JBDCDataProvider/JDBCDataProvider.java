package DataProviders.JBDCDataProvider;

import DataProviders.dataProvidersBase.DataCaster;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCDataProvider extends DataCaster {

    private String driverClass;
    private String connectionString;
    private String user;
    private String password;

    private String statementQuery;


    @DataProvider
    public Object[][] jdbcDataProvider(Method m) throws SQLException, ClassNotFoundException {

        fillConnectionDataFromAnnotation(m);

        return getTestData(m);
    }

    private Object[][] getTestDataFromResultSet(ResultSet data, Method m) throws SQLException {

        List<Object[]> result = new ArrayList<Object[]>();

        while (data.next()){
            Object[] parameters = new Object[m.getParameterTypes().length];
            for(Object obj : parameters) obj = null;

            for(int i=0; i < m.getParameterTypes().length; i++ ){
                 parameters[i] = castDataType(m.getParameterTypes()[i], data.getObject(i+1));
            }
            result.add(parameters);

        }
        return result.toArray(new Object[result.size()][]);
    }

    private Object[][] getTestData(Method m) throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        Object[][] result;

        try {
            Class.forName(driverClass);

            if (user.equals("") && password.equals("")) {
                connection = DriverManager.getConnection(connectionString);
            } else {
                connection = DriverManager.getConnection(connectionString, user, password);
            }

            statement = connection.createStatement();

            result = getTestDataFromResultSet(statement.executeQuery(statementQuery),m);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
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

        return result;
    }

    private void fillConnectionDataFromAnnotation(Method m) {

        if (!(m.isAnnotationPresent(JDBCDataSource.class))) {
            throw new Error("There is no @JDBCDataSource annotation on method " + m);
        }

        JDBCDataSource dataSource = m.getAnnotation(JDBCDataSource.class);
        driverClass = dataSource.driverClass();
        connectionString = dataSource.connectionURL();
        user = dataSource.user();
        password = dataSource.password();
        statementQuery = dataSource.statementQuery();


    }
}