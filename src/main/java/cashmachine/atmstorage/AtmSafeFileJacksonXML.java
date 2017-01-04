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
    xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    ArrayList<MoneyPack> wholeList = new ArrayList<>();
    if (!safe.isEmpty()) {
      for (ArrayList<MoneyPack> mpList : safe.values()) {
        for (MoneyPack mp : mpList) {
          wholeList.add(mp);
        }
      }
    }

    try {
      xmlMapper.writeValue(new File(filename), wholeList);
    } catch (IOException exception) {
      System.out.println("Error write to file: " + filename + " " + exception);
    }
  }

  public HashMap<String, ArrayList<MoneyPack>> loadSafe() throws Exception {
    ATMStorage moneyStorage = new ATMStorage("memory");
    moneyStorage.emptyStorage();
    ArrayList<MoneyPack> wholeList = new ArrayList<>();
    String safeStr;
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    File file = new File(filename);
    if (!file.exists()) {
      saveSafe(moneyStorage.getMoneyStorage());
      return moneyStorage.getMoneyStorage();
    }

    try (BufferedReader in = new BufferedReader(new FileReader(file))) {
      safeStr = in.readLine();
      if (!safeStr.isEmpty()) {
        wholeList = xmlMapper.readValue(safeStr, new TypeReference<ArrayList<MoneyPack>>() {});
      }
    } catch (IOException exception) {
      System.out.println("Error read from file: " + filename + " " + exception);
    }

    for (MoneyPack mp : wholeList) {
      moneyStorage.store(mp);
    }
    return moneyStorage.getMoneyStorage();
  }
}
