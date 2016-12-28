package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.HashMap;

public class AtmSafeFileObject implements AtmSafe {
  private String filename = "safe.ser";

  public AtmSafeFileObject() {

  }

  public AtmSafeFileObject(String filename) {
    this.filename = filename;
  }

  public void saveSafe(HashMap<String, ArrayList<MoneyPack>> safe) throws IOException {

    try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(filename))) {
      out.writeObject(safe);
    } catch (IOException exception) {
      System.out.println("Error open file: " + filename + " " + exception);
    }
  }

  @SuppressWarnings("unchecked")
  public HashMap<String, ArrayList<MoneyPack>> loadSafe() throws IOException {
    HashMap<String, ArrayList<MoneyPack>> moneyStorage = new HashMap<>();

    File file = new File(filename);
    if (!file.exists()) {
      saveSafe(moneyStorage);
    }

    try (ObjectInput in = new ObjectInputStream(new FileInputStream(filename))) {
      moneyStorage = (HashMap<String, ArrayList<MoneyPack>>) in.readObject();
    } catch (IOException exception) {
      System.out.println("Error open file: " + filename + " " + exception);
    } catch (ClassNotFoundException exception) {
      exception.printStackTrace();
    }
    return moneyStorage;
  }
}
