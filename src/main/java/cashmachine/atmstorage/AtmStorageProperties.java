package cashmachine.atmstorage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AtmStorageProperties {

  /*
  public static void setStorageType(String storageType) throws Exception {
    Properties properties = new Properties();

    try {
      properties.setProperty("storageType", storageType);
      properties.store(new FileOutputStream("atm.properties"), "ATM properties file.");
    } catch (IOException exception) {
      System.out.println("Error open atm.properties file");
    }
  }
  */

  public static String getStorageType() throws Exception {
    String storageType = "";
    Properties properties = new Properties();
    String propFileName = "/atm.properties";

    InputStream inputStream = AtmStorageProperties.class.getResourceAsStream(propFileName);

    if (inputStream != null) {
      properties.load(inputStream);
      storageType = properties.getProperty("storageType");
      inputStream.close();
    } else {
      System.out.println("Property file " + propFileName + " not found in the classpath!");
    }
    return storageType;
  }

}
