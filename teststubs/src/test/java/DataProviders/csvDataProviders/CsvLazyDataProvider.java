package DataProviders.csvDataProviders;

import au.com.bytecode.opencsv.CSVReader;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Created by sunny_IT on 1/31/2017.
 */
public class CsvLazyDataProvider {

    @DataProvider
    public static Iterator<Object[]> lazyCsvDataProvider(Method m) throws IOException {

        if(m.isAnnotationPresent(CsvDataSource.class)){
            CsvDataSource dataSource = m.getAnnotation(CsvDataSource.class);
            String sourcePath ="";
            char fileSeparator=' ';
            sourcePath = dataSource.sourcePath();
            fileSeparator = dataSource.separator();
            if(sourcePath.equals("user.dir")) {
                sourcePath = System.getProperty("user.dir") + System.getProperty("file.separator");
            }

            if(fileSeparator == ' '){
                return new CsvFileIterator(new File(sourcePath + dataSource.value()), m.getParameterTypes().length);

            }
            return new CsvFileIterator(new File(sourcePath + dataSource.value()), fileSeparator, m.getParameterTypes().length);
        }

        throw new Error("There is no @CsvDataSource annotation on method " + m);
    }

    private static class CsvFileIterator implements Iterator<Object[]> {

        private final CSVReader reader;
        private int length;
        private String[] nextLine;
        private boolean firstLine = true;

        public CsvFileIterator(File csvFile, int length) throws FileNotFoundException {
            this.length = length;
            reader = new CSVReader(new FileReader(csvFile));
        }

        public CsvFileIterator(File csvFile,  char separator, int length) throws FileNotFoundException {
            this.length = length;
            reader = new CSVReader(new FileReader(csvFile), separator);
        }
        public boolean hasNext() {
            try {
                if(firstLine){
                    firstLine = false;
                    return true;
                }

                nextLine = reader.readNext();
                return nextLine != null;
            } catch (IOException e) {
                return false;
            }
        }


        public Object[] next() {
            if (nextLine == null) {
                return null;
            }
           // System.out.println(Arrays.toString(nextLine));
            Object[] parameters = new Object[length];
            for (int i = 0; i < length; i++) {
                parameters[i] = i < nextLine.length ? nextLine[i] : null;
            }
            return parameters;
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
