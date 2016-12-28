package cashmachine.atmstorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.util.Properties;

public class AtmStorageProperties {
  private String propFileName = "atm.properties";

  public AtmStorageProperties() throws Exception {
    this("");
  }

  public AtmStorageProperties(String environment) throws Exception {
    if (environment.equals("test")) {
      propFileName = "atm-test.properties";
    }

    File file = new File(propFileName);
    if (!file.exists()) {
      System.out.println("Property file not found in the classpath! Create default config: " + propFileName);
      createDefaultProperties();
    }
  }

  private void createDefaultProperties() throws Exception {
    Properties properties = new Properties();

    try {
      properties.setProperty("storageType", "object");
      properties.setProperty("objectFileName", "atm.ser");
      properties.setProperty("jsonFileName", "atm.json");
      properties.setProperty("xmlFileName", "atm.xml");
      properties.setProperty("h2_db.uri", "h2");
      properties.setProperty("h2_db.username", "su");
      properties.setProperty("h2_db.password", "su");

      properties.store(new FileOutputStream(propFileName), "ATM properties file.");
    } catch (IOException exception) {
      System.out.println("Error open atm.properties file. " + exception);
    }
  }

  public String getProperty(String property) throws Exception {
    Properties properties = new Properties();
    String propertyValue = "";

    FileReader fileReader = new FileReader(propFileName);

    if (fileReader != null) {
      properties.load(fileReader);
      propertyValue = properties.getProperty(property);
      fileReader.close();
    } else {
      System.out.println("Property file " + propFileName + " not found in the classpath!");
      createDefaultProperties();
    }
    return propertyValue;
  }
}
