package DataProviders.xlsDataProvider;


import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by sunny_IT on 2/1/2017.
 */
public class TestSample {

    @Test(enabled = true, dataProvider = "XLSDataProvider", dataProviderClass = XLSDataProvider.class)
    @XLSDataSource(value = "Book1.xlsx")
    public void testXLSXSimple(String par1, float par2, String par3, Date dt) {
        System.out.println("Hello, " + par1 + "," + par2 + "," + par3 + ",   :" + dt);
    }


    @Test(enabled = false, dataProvider = "XLSDataProvider", dataProviderClass = XLSDataProvider.class)
    @XLSDataSource(value = "Book1.xlsx", mapToParameters = true, sheetIndex = 1)
    public void testXLSXMatcher(String par1, float par2, String par3, Date dt) {
        System.out.println("Hello, " + par1 + "," + par2 + "," + par3 + ",   :" + dt);
    }

    @Test(dataProvider = "lazyXLSDataProvider", dataProviderClass = XLSLazyDataProvider.class)
    @XLSDataSource(value = "Book1.xlsx")
    public void testLazyProvider(String par1, float par2, String par3, Date dt) {
        System.out.println("Hello, " + par1 + "," + par2 + "," + par3 + ",   :" + dt);
    }


}
