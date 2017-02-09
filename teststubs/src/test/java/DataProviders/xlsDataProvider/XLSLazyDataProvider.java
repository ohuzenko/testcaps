package DataProviders.xlsDataProvider;


import DataProviders.dataProvidersBase.DataCaster;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;


public class XLSLazyDataProvider extends DataCaster {

    @DataProvider
    public static Iterator<Object[]> lazyXLSDataProvider(Method m) throws IOException {

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
                return new   XLSFileIterator(sourcePath + dataSource.value(), m, sheetName);
            }


            return new  XLSFileIterator(sourcePath + dataSource.value(), m, dataSource.sheetIndex());
        }


        throw new Error("There is no @CsvDataSource annotation on method " + m);
    }

    private static class XLSFileIterator implements Iterator<Object[]> {
        private Iterator<Row> reader;
        private Method m;
        private Row row;
        private boolean firstLine = true;
        Class<?>[] paramTypes;

        public XLSFileIterator(String s, Method m, String sheetName) throws IOException {

           reader =  Reader.getIteratorInstanceFrom(s).getRowIterator(sheetName);
            setMethodProperties(m);
        }

        public XLSFileIterator(String s, Method m, int i) throws IOException {

            reader = Reader.getIteratorInstanceFrom(s).getRowIterator(i);
            setMethodProperties(m);
        }

        private void setMethodProperties(Method m) {
            this.m = m;
            paramTypes = m.getParameterTypes();
        }


        public boolean hasNext() {
            try {

                if(firstLine){
                    firstLine = false;
                    return true;
                }

                row = reader.next();
                return row != null;

            } catch (Exception e) {
                return false;
            }
        }

        public Object[] next() {

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

            return parameters;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}

