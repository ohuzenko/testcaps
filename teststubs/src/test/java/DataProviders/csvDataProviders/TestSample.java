package DataProviders.csvDataProviders;


import org.testng.annotations.Test;

/**
 * Created by sunny_IT on 1/31/2017.
 */
@Test
public class TestSample {

    @Test(dataProvider = "csvDataProvider", dataProviderClass = CsvDataProvider.class)
    @CsvDataSource(value = "data.csv", sourcePath = "user.dir", separator = ';')
    public void testCsv(String par1, String par2, int par3) {
        System.out.println("Hello, " + par1 + "," + par2 + "," + par3);
    }

    @Test(dataProvider = "lazyCsvDataProvider", dataProviderClass = CsvLazyDataProvider.class)
    @CsvDataSource(value = "data.csv", separator = ';')
    public void testLazyCsv(String par1, String par2, String par3) {
        System.out.println("Hello, " + par1 + "," + par2 + "," + par3);
    }
}
