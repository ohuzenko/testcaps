package DataProviders.xlsDataProvider;


import java.io.IOException;


public class Reader {




    public static RowIterator getIteratorInstanceFrom(String fullFileName) throws IOException {

        if(fullFileName.endsWith(".xlsx")) {return new XLSXReader(fullFileName);

        }

        if(fullFileName.endsWith(".xls")) {return new XLSReader(fullFileName);
        }


        throw new IOException("Cannot get iterator from non excel file.");

    }


}
