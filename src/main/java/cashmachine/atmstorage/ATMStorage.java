package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;
import cashmachine.money.MoneyPackSortByValueDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ATMStorage {
  private AtmSafe atmSafe = new AtmSafeFileObject();
  private HashMap<String, ArrayList<MoneyPack>> moneyStorage;

  public ATMStorage() throws Exception {
    this("");
  }

  public ATMStorage(String environment) throws Exception {

    if (environment.equals("memory")) {
      atmSafe = new AtmSafeFileMemory();
      moneyStorage = atmSafe.loadSafe();
      return;
    }

    AtmStorageProperties properties = new AtmStorageProperties(environment);

    switch (properties.getProperty("storageType")) {
      case "object": atmSafe = new AtmSafeFileObject(properties.getProperty("objectFileName"));
        break;
      case "xml": atmSafe = new AtmSafeFileJacksonXML(properties.getProperty("xmlFileName"));
        break;
      case "json": atmSafe = new AtmSafeFileJackson(properties.getProperty("jsonFileName"));
        break;
      case "memory": atmSafe = new AtmSafeFileMemory();
        break;
      default: atmSafe = new AtmSafeFileMemory();
        break;
    }
    moneyStorage = atmSafe.loadSafe();
  }


  public void store(MoneyPack moneyPack) throws IOException {

    moneyPack = new MoneyPack(moneyPack.getCurrency(), moneyPack.getValue(), moneyPack.getAmount());

    if (moneyStorage.containsKey(moneyPack.getCurrency())) {
      ArrayList<MoneyPack> mpList = moneyStorage.get(moneyPack.getCurrency());

      if (mpList.size() == 0) {
        mpList.add(moneyPack);
        return;
      }

      boolean findValue = false;
      for (int i = 0; i < mpList.size(); i++) {
        if (mpList.get(i).getValue() == moneyPack.getValue()) {
          mpList.get(i).setAmount(mpList.get(i).getAmount() + moneyPack.getAmount());
          findValue = true;
          break;
        }
      }
      if (!findValue) {
        mpList.add(moneyPack);
      }

      moneyStorage.get(moneyPack.getCurrency()).sort(new MoneyPackSortByValueDesc());
    } else {
      ArrayList<MoneyPack> tempArrayList = new ArrayList<>();
      tempArrayList.add(moneyPack);
      moneyStorage.put(moneyPack.getCurrency(), tempArrayList);
    }

    atmSafe.saveSafe(moneyStorage);
  }

  @SuppressWarnings("unchecked")
  public List<MoneyPack> take(String currency, int money) throws IOException {
    ArrayList<MoneyPack> moneyList = new ArrayList<>();

    if (!moneyStorage.containsKey(currency)) {
      return Collections.emptyList();
    }

    if (getAmount(currency) < money) {
      return Collections.emptyList();
    }

    int summ = 0;
    int buffer = money;
    for (int i = 0; i < moneyStorage.get(currency).size(); i++) {
      if (moneyStorage.get(currency).get(i).getValue() > buffer) {
        continue;
      }
      if (buffer / moneyStorage.get(currency).get(i).getValue() > moneyStorage.get(currency).get(i).getAmount()) {
        summ += moneyStorage.get(currency).get(i).getAmount() * moneyStorage.get(currency).get(i).getValue();
        moneyList.add(new MoneyPack(currency, moneyStorage.get(currency).get(i).getValue(),
            moneyStorage.get(currency).get(i).getAmount()));
        buffer = money - summ;
        continue;
      }

      summ += (buffer / moneyStorage.get(currency).get(i).getValue()) * moneyStorage.get(currency).get(i).getValue();
      moneyList.add(new MoneyPack(currency, moneyStorage.get(currency).get(i).getValue(),
          buffer / moneyStorage.get(currency).get(i).getValue()));
      buffer = money - summ;
    }
    if (summ == money) {
      takeFromStorage(moneyList);
      atmSafe.saveSafe(moneyStorage);
      return (List<MoneyPack>) moneyList.clone();
    }
    return Collections.emptyList();
  }

  private void takeFromStorage(ArrayList<MoneyPack> moneyList) {
    for (MoneyPack mp : moneyList) {
      for (int i = 0; i < moneyStorage.get(mp.getCurrency()).size(); i++) {
        if (moneyStorage.get(mp.getCurrency()).get(i).getValue() == mp.getValue()) {
          moneyStorage.get(mp.getCurrency()).get(i).setAmount(moneyStorage.get(mp.getCurrency()).get(i).getAmount()
              - mp.getAmount());
          if (moneyStorage.get(mp.getCurrency()).get(i).getAmount() == 0) {
            moneyStorage.get(mp.getCurrency()).remove(i);
          }
        }
      }
    }
    flushMoneyStorage();
  }

  private void flushMoneyStorage() {
    Iterator<Map.Entry<String, ArrayList<MoneyPack>>> it = moneyStorage.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, ArrayList<MoneyPack>> entry = it.next();
      ArrayList<MoneyPack> value = entry.getValue();
      if (value.isEmpty()) {
        it.remove();
      }
    }
  }

  public List<MoneyPack> showContent() {
    ArrayList<MoneyPack> wholeList = new ArrayList<>();
    for (ArrayList<MoneyPack> mpList : moneyStorage.values()) {
      for (MoneyPack mp : mpList) {
        wholeList.add(mp);
      }
    }
    return wholeList;
  }

  public HashMap<String, ArrayList<MoneyPack>> getMoneyStorage() {
    return moneyStorage;
  }

  public int getAmount(String currency) {
    if (!moneyStorage.containsKey(currency)) {
      return 0;
    }

    int summ = 0;
    for (int i = 0; i < moneyStorage.get(currency).size(); i++) {
      summ +=  moneyStorage.get(currency).get(i).getValue() * moneyStorage.get(currency).get(i).getAmount();
    }
    return summ;
  }

  public void emptyStorage() throws IOException {
    moneyStorage.clear();
    atmSafe.saveSafe(moneyStorage);
  }

  public void emptyStorage(String currency) throws IOException {
    if (moneyStorage.containsKey(currency)) {
      moneyStorage.remove(currency);
      atmSafe.saveSafe(moneyStorage);
    }
  }

}
