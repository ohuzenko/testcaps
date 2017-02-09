package DataProviders.xlsDataProvider;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by sunny_IT on 1/21/2017.
 */

public class XLSXReader implements RowIterator {

    private String fullFileName;

    public XLSXReader(String fullFileName) {
        this.fullFileName = fullFileName;
    }


     public  Iterator<Row> getRowIterator(int sheetIndex ) throws IOException {

        XSSFWorkbook workbook = getXSSFSheets();
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

            return sheet.iterator();

        }

    public Iterator<Row> getRowIterator(String sheetName) throws IOException {

        XSSFWorkbook workbook = getXSSFSheets();
        XSSFSheet sheet = workbook.getSheet(sheetName);

        return sheet.iterator();


    }

    private XSSFWorkbook getXSSFSheets() throws IOException {

        FileInputStream file = new FileInputStream(new File(fullFileName));
        return new XSSFWorkbook(file);
    }
}
