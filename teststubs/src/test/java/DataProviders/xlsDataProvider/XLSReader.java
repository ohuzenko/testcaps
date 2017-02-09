package DataProviders.xlsDataProvider;

/**
 * Created by sunny_IT on 1/21/2017.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XLSReader implements RowIterator {
    private String fullFileName;



    public XLSReader(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    public  Iterator<Row> getRowIterator(int sheetIndex ) throws IOException {

        HSSFWorkbook workbook = getHssfWorkbook();
        HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet.iterator();

    }

    public Iterator<Row> getRowIterator(String sheetName) throws IOException {
        HSSFWorkbook workbook = getHssfWorkbook();
        HSSFSheet sheet = workbook.getSheet(sheetName);
        return sheet.iterator();
    }

    private HSSFWorkbook getHssfWorkbook() throws IOException {
        FileInputStream file = new FileInputStream(new File(fullFileName));
        return new HSSFWorkbook(file);
    }


}
