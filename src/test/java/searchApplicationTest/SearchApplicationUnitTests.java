package searchApplicationTest;

import org.junit.jupiter.api.Test;
import searchApplication.SearchAppConfigData;
import searchApplication.SearchApplication;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchApplicationUnitTests {
    /**
     * unit test for inputArgumentIsValid() method of class SearchApplication.
     * well-formed input argument.
     */
    @Test
    public void testInputArgumentIsValid1() {
        String inputArgumentString = "-input Search_Application_config.json";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(true, SearchApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class SearchApplication.
     * no space between -input and config file name.
     */
    @Test
    public void testInputArgumentIsValid2() {
        String inputArgumentString = "-inputSearch_Application_config.json";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, SearchApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class SearchApplication.
     * missing config file extension.
     */
    @Test
    public void testInputArgumentIsValid3() {
        String inputArgumentString = "-input Search_Application_config.";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, SearchApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class SearchApplication.
     * wrong config file type.
     */
    @Test
    public void testInputArgumentIsValid4() {
        String inputArgumentString = "-input Search_Application_config.jon";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, SearchApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class SearchApplication.
     * incorrect flag in input argument.
     */
    @Test
    public void testInputArgumentIsValid5() {
        String inputArgumentString = "-inut Search_Application_config.json";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, SearchApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for getExtension() method of class SearchApplication.
     * file name with extension.
     */
    @Test
    public void testGetExtension1() {
        String inputArgumentString = "Search_Application_config.json";
        assertEquals(".json", SearchApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class SearchApplication.
     * file name with some extension.
     */
    @Test
    public void testGetExtension2() {
        String inputArgumentString = "Search_Application_config.jso";
        assertEquals(".jso", SearchApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class SearchApplication.
     * file name with no extension.
     */
    @Test
    public void testGetExtension3() {
        String inputArgumentString = "Search_Application_config.";
        assertEquals("", SearchApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class SearchApplication.
     * file name with no extension.
     */
    @Test
    public void testGetExtension4() {
        String inputArgumentString = "Search_Application_configjson";
        assertEquals("", SearchApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class SearchApplication.
     * file name with only extension.
     */
    @Test
    public void testGetExtension5() {
        String inputArgumentString = ".json";
        assertEquals("", SearchApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test that tests getQaFile() method of SearchAppConfigData class.
     */
    @Test
    public void testReadInputConfigFile1() {
        String fileName = "Search_Application_config_smallerFiles.json";
        SearchAppConfigData searchAppConfigData = new SearchAppConfigData();
        searchAppConfigData.setPortNo(8080);
        searchAppConfigData.setQaFile("qa_Cell_Phones_and_Accessories_5_smaller.json");
        searchAppConfigData.setReviewFile("review_Cell_Phones_and_Accessories_smaller.json");

        try {
            assertEquals(searchAppConfigData.getQaFile(), SearchApplication.readInputConfigFile(fileName).getQaFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * unit test that tests getReviewFile() method of SearchAppConfigData class.
     */
    @Test
    public void testReadInputConfigFile2() {
        String fileName = "Search_Application_config_smallerFiles.json";
        SearchAppConfigData searchAppConfigData = new SearchAppConfigData();
        searchAppConfigData.setPortNo(8080);
        searchAppConfigData.setQaFile("qa_Cell_Phones_and_Accessories_5_smaller.json");
        searchAppConfigData.setReviewFile("review_Cell_Phones_and_Accessories_smaller.json");

        try {
            assertEquals(searchAppConfigData.getReviewFile(),
                    SearchApplication.readInputConfigFile(fileName).getReviewFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * unit test that tests getPortNo() method of SearchAppConfigData class.
     */
    @Test
    public void testReadInputConfigFile() {
        String fileName = "Search_Application_config_smallerFiles.json";
        SearchAppConfigData searchAppConfigData = new SearchAppConfigData();
        searchAppConfigData.setPortNo(8080);
        searchAppConfigData.setQaFile("qa_Cell_Phones_and_Accessories_5_smaller.json");
        searchAppConfigData.setReviewFile("review_Cell_Phones_and_Accessories_smaller.json");

        try {
            assertEquals(searchAppConfigData.getPortNo(), SearchApplication.readInputConfigFile(fileName).getPortNo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * unit test for isValid() method of class SearchAppConfigData.
     */
    @Test
    public void testIsValidOfSearchAppConfigData1() {
        String fileName = "Search_Application_config_smallerFiles.json";
        SearchAppConfigData searchAppConfigData = new SearchAppConfigData();
        try {
            searchAppConfigData = SearchApplication.readInputConfigFile(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(true, searchAppConfigData.isValid());
    }

    /**
     * unit test for isValid() method of class SearchAppConfigData.
     * SearchAppConfigData object does not contain port number.
     */
    @Test
    public void testIsValidOfSearchAppConfigData2() {
        SearchAppConfigData searchAppConfigData = new SearchAppConfigData();
        searchAppConfigData.setQaFile("qa_Cell_Phones_and_Accessories_5_smaller.json");
        searchAppConfigData.setReviewFile("review_Cell_Phones_and_Accessories_smaller.json");
        assertEquals(false, searchAppConfigData.isValid());
    }


    /**
     * unit test for isValid() method of class SearchAppConfigData.
     * SearchAppConfigData object has wrong port number.
     */
    @Test
    public void testIsValidOfSearchAppConfigData3() {
        SearchAppConfigData searchAppConfigData = new SearchAppConfigData();
        searchAppConfigData.setPortNo(8976);
        searchAppConfigData.setQaFile("qa_Cell_Phones_and_Accessories_5_smaller.json");
        searchAppConfigData.setReviewFile("review_Cell_Phones_and_Accessories_smaller.json");
        assertEquals(false, searchAppConfigData.isValid());
    }

}
