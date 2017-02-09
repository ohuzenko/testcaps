package DataProviders.csvDataProviders;

import DataProviders.dataProvidersBase.DataCaster;
import au.com.bytecode.opencsv.CSVReader;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CsvDataProvider extends DataCaster {



    @DataProvider
    public static Object[][] csvDataProvider(Method m) throws IOException {

        CSVReader reader = getReaderFromAnnotation(m);
        if (reader != null) {

            return getParameters(m, reader);
        }

        throw new Error("There is no @CsvDataSource annotation on method " + m);
    }


    private static Object[][] getParameters(Method m, CSVReader reader) throws IOException {

        List<Object[]> result = new ArrayList<Object[]>();
        String[] nextLine;

        Class<?>[] paramTypes = m.getParameterTypes();

        DataProviders.csvDataProviders.CsvDataSource dataSource = m.getAnnotation(DataProviders.csvDataProviders.CsvDataSource.class);
        if(dataSource.skipFirstString()) {
            reader.readNext();
        }

        while ((nextLine = reader.readNext()) != null) {

            Object[] parameters = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                parameters[i] = i < nextLine.length ? castDataType(paramTypes[i], nextLine[i]) : null;
            }
            result.add(parameters);
        }

        return result.toArray(new Object[result.size()][]);
    }



    private static CSVReader getReaderFromAnnotation(Method m) throws FileNotFoundException {
        CSVReader reader = null;
        String sourcePath ="";
        char fileSeparator=' ';
        if (m.isAnnotationPresent(CsvDataSource.class)) {
            CsvDataSource dataSource = m.getAnnotation(CsvDataSource.class);
            sourcePath = dataSource.sourcePath();
            fileSeparator = dataSource.separator();
            if(sourcePath.equals("user.dir")) {
                sourcePath = System.getProperty("user.dir") + System.getProperty("file.separator");
            }

            if(fileSeparator == ' '){
                reader = new CSVReader(new FileReader(new File(sourcePath + dataSource.value())));

            }else {
                reader = new CSVReader(new FileReader(new File(sourcePath + dataSource.value())), fileSeparator);
            }
        }

        return reader;
    }


}
