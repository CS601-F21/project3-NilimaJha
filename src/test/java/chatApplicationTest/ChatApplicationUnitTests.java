package chatApplicationTest;

import chatApplication.ChatAppConfigData;
import chatApplication.ChatApplication;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatApplicationUnitTests {

    /**
     * unit test for inputArgumentIsValid() method of class ChatApplication.
     * well-formed input argument.
     */
    @Test
    public void testInputArgumentIsValid1() {
        String inputArgumentString = "-input Chat_Application_config.json";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(true, ChatApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class ChatApplication.
     * no space between -input and config file name.
     */
    @Test
    public void testInputArgumentIsValid2() {
        String inputArgumentString = "-inputChat_Application_config.json";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, ChatApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class ChatApplication.
     * missing config file extension.
     */
    @Test
    public void testInputArgumentIsValid3() {
        String inputArgumentString = "-input Chat_Application_config.";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, ChatApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class ChatApplication.
     * wrong config file type.
     */
    @Test
    public void testInputArgumentIsValid4() {
        String inputArgumentString = "-input Chat_Application_config.jon";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, ChatApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for inputArgumentIsValid() method of class ChatApplication.
     * incorrect flag in input argument.
     */
    @Test
    public void testInputArgumentIsValid5() {
        String inputArgumentString = "-iput Chat_Application_config.json";
        String[] inputArgumentArray = inputArgumentString.split(" ");
        assertEquals(false, ChatApplication.inputArgumentIsValid(inputArgumentArray));
    }

    /**
     * unit test for getExtension() method of class ChatApplication.
     * file name with extension.
     */
    @Test
    public void testGetExtension1() {
        String inputArgumentString = "Chat_Application_config.json";
        assertEquals(".json", ChatApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class ChatApplication.
     * file name with some extension.
     */
    @Test
    public void testGetExtension2() {
        String inputArgumentString = "Chat_Application_config.jso";
        assertEquals(".jso", ChatApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class ChatApplication.
     * file name with no extension.
     */
    @Test
    public void testGetExtension3() {
        String inputArgumentString = "Chat_Application_config.";
        assertEquals("", ChatApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class ChatApplication.
     * file name with no extension.
     */
    @Test
    public void testGetExtension4() {
        String inputArgumentString = "Chat_Application_configjson";
        assertEquals("", ChatApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for getExtension() method of class ChatApplication.
     * file name with only extension.
     */
    @Test
    public void testGetExtension5() {
        String inputArgumentString = ".json";
        assertEquals("", ChatApplication.getExtension(inputArgumentString));
    }

    /**
     * unit test for isValid() method of class ChatAppConfigData.
     */
    @Test
    public void testIsValidOfSearchAppConfigData1() {
        String fileName = "Chat_Application_config.json";
        ChatAppConfigData chatAppConfigData = new ChatAppConfigData();
        try {
            chatAppConfigData = ChatApplication.readInputConfigFile(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(true, chatAppConfigData.isValid());
    }

    /**
     * unit test for isValid() method of class ChatAppConfigData.
     */
    @Test
    public void testIsValidOfSearchAppConfigData2() {
        ChatAppConfigData chatAppConfigData = new ChatAppConfigData();
        chatAppConfigData.setSlackChannelId("asdfgh");
        chatAppConfigData.setTokenFileName("qwerty");
        assertEquals(false, chatAppConfigData.isValid());
    }
}
