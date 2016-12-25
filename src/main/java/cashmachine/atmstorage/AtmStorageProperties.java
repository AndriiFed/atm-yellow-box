package cashmachine.atmstorage;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class AtmStorageProperties {

  public static void setStorageType(String storageType) throws Exception {
    Properties properties = new Properties();

    try {
      properties.setProperty("storageType", storageType);
      properties.store(new FileOutputStream("atm.properties"), "ATM properties file.");
    } catch (IOException exception) {
      System.out.println("Error open atm.properties file");
    }
  }

  public static String getStorageType() throws Exception {
    String storageType = "";
    Properties properties = new Properties();

    try {
      properties.load(new FileReader("atm.properties"));
      storageType = properties.getProperty("storageType");
    } catch (IOException exception) {
      System.out.println("Error open atm.properties file");
    }
    return storageType;
  }



}
