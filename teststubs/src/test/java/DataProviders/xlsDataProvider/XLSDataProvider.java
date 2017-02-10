package DataProviders.xlsDataProvider;

import DataProviders.dataProvidersBase.DataCaster;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;


public class XLSDataProvider extends DataCaster {

    @DataProvider
    public static Object[][] XLSDataProvider(Method m) throws Exception {

        Iterator<Row> rows = getIteratorFromAnnotation(m);

        if (rows != null) {
            XLSDataSource dataSource = m.getAnnotation(XLSDataSource.class);
            if (dataSource.mapToParameters()) return getParametersByNames(m, rows);

            return getParameters(m, rows);
        }

        throw new Error("There is no @CsvDataSource annotation on method " + m);
    }

    private static Object[][] getParameters(Method m, Iterator<Row> rows) throws IOException {

        List<Object[]> result = new ArrayList<Object[]>();
        Class<?>[] paramTypes = m.getParameterTypes();
        while (rows.hasNext()) {

            Row row = rows.next();
            int i = 0;
            Iterator<Cell> cells = row.iterator();

            Object[] parameters = new Object[paramTypes.length];


            while (cells.hasNext()) {
                if (i < paramTypes.length) {
                    Cell cell = cells.next();
                    parameters[i] = castDataType(paramTypes[i], cell.toString());
                    i++;
                }

            }

            for (int j = i; i < paramTypes.length; i++) {
                parameters[j] = null;
            }
            result.add(parameters);

        }


        return result.toArray(new Object[result.size()][]);
    }

    private static Iterator<Row> getIteratorFromAnnotation(Method m) throws IOException {

        Iterator<Row> rows = null;
        String sourcePath;
        String sheetName;


        if (m.isAnnotationPresent(XLSDataSource.class)) {
            XLSDataSource dataSource = m.getAnnotation(XLSDataSource.class);
            sourcePath = dataSource.sourcePath();
            if (sourcePath.equals("user.dir")) {
                sourcePath = System.getProperty("user.dir") + System.getProperty("file.separator");
            }

            sheetName = dataSource.sheetName();
            if (!sheetName.equals("")) {
                return Reader.getIteratorInstanceFrom(sourcePath + dataSource.value()).getRowIterator(sheetName);
            }


            return Reader.getIteratorInstanceFrom(sourcePath + dataSource.value()).getRowIterator(dataSource.sheetIndex());
        }


        return rows;
    }


    private static Object[][] getParametersByNames(Method m, Iterator<Row> rows) throws Exception {

        List<Object[]> result = new ArrayList<Object[]>();

        String[] parametersInFunction = new String[m.getParameterTypes().length];
        String[] parametersInExcel = new String[m.getParameterTypes().length];

        for (int i = 0; i < m.getParameterTypes().length; i++) {
            parametersInFunction[i] = m.getParameters()[i].getName();
        }

        if (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.iterator();
            int i = 0;
            while (cells.hasNext()) {
                if (i < m.getParameterTypes().length) {
                    Cell cell = cells.next();
                    parametersInExcel[i] = cell.toString();
                    i++;
                }

            }

            while (rows.hasNext()) {

                row = rows.next();
                i = 0;
                cells = row.iterator();
                int index = 0;
                Object[] parameters = new Object[parametersInFunction.length];


                while (cells.hasNext()) {
                    if (i < parametersInFunction.length) {
                        Cell cell = cells.next();
                        String paramName = parametersInExcel[i];

                        while (index < parametersInFunction.length && !parametersInFunction[index].equals(paramName))
                            index++;

                        if (index >= parametersInFunction.length)
                            throw new RuntimeException("Cannot find parameter matcher. Verify input data.");


                        parameters[index] = castDataType(m.getParameterTypes()[index], cell.toString());
                        i++;
                        index = 0;
                    }

                }
                result.add(parameters);
            }


            return result.toArray(new Object[result.size()][]);
        }


        return null;
    }


}
