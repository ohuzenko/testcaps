package DataProviders.xlsDataProvider;

import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by sunny_IT on 1/21/2017.
 */
public interface RowIterator {

    Iterator<Row> getRowIterator(int sheetIndex ) throws IOException;
    Iterator<Row> getRowIterator(String sheetName ) throws IOException;

}
