package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class AtmSafeFileJacksonXML implements AtmSafe {
  private String filename = "safe.xml";

  public AtmSafeFileJacksonXML() {

  }

  public AtmSafeFileJacksonXML(String filename) {
    this.filename = filename;
  }

  public void saveSafe(HashMap<String, ArrayList<MoneyPack>> safe) throws IOException {
    ObjectMapper xmlMapper = new XmlMapper();

    try {
      xmlMapper.writeValue(new File(filename), safe);
    } catch (IOException exception) {
      System.out.println("Error write to file: " + filename + " " + exception);
    }
  }

  public HashMap<String, ArrayList<MoneyPack>> loadSafe() throws IOException {
    HashMap<String, ArrayList<MoneyPack>> moneyStorage = new HashMap<>();
    String safeStr;
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    File file = new File(filename);
    if (!file.exists()) {
      saveSafe(moneyStorage);
      return moneyStorage;
    }

    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
      safeStr = in.readLine();
      if (!safeStr.isEmpty()) {
        moneyStorage = xmlMapper.readValue(safeStr, new TypeReference<HashMap<String, ArrayList<MoneyPack>>>() {});
      }
    } catch (IOException exception) {
      System.out.println("Error read from file: " + filename + " " + exception);
    }
    return moneyStorage;
  }
}
