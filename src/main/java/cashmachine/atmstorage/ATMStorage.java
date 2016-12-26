package cashmachine.atmstorage;

import cashmachine.atmstorage.AtmStorageProperties;
import cashmachine.money.MoneyPack;
import cashmachine.money.MoneyPackSortByValueDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




public class ATMStorage {
  private AtmSafe atmSafe = new AtmSafeFileObject();
  private HashMap<String, ArrayList<MoneyPack>> moneyStorage = atmSafe.loadSafe();

  public ATMStorage() throws Exception {
    switch (AtmStorageProperties.getStorageType()) {
      case "object": atmSafe = new AtmSafeFileObject();
        break;
      case "bytes": atmSafe = new AtmSafeFileByte();
        break;
      //case "jackson": atmSafe = new AtmSafeJackson();
        //break;
      default: atmSafe = new AtmSafeFileObject();
        break;
    }
  }

  public void store(MoneyPack moneyPack) throws IOException {

    moneyPack = new MoneyPack(moneyPack.getCurrency(), moneyPack.getValue(), moneyPack.getAmount());

    if (moneyStorage.containsKey(moneyPack.getCurrency())) {
      ArrayList<MoneyPack> mplist = moneyStorage.get(moneyPack.getCurrency());

      if (mplist.size() == 0) {
        mplist.add(moneyPack);
        return;
      }

      boolean findValue = false;
      for (int i = 0; i < mplist.size(); i++) {
        if (mplist.get(i).getValue() == moneyPack.getValue()) {
          mplist.get(i).setAmount(mplist.get(i).getAmount() + moneyPack.getAmount());
          findValue = true;
          break;
        }
      }
      if (!findValue) {
        mplist.add(moneyPack);
      }

      moneyStorage.get(moneyPack.getCurrency()).sort(new MoneyPackSortByValueDesc());
    } else {
      ArrayList<MoneyPack> tempArrayList = new ArrayList<>();
      tempArrayList.add(moneyPack);
      moneyStorage.put(moneyPack.getCurrency(), tempArrayList);
    }

    atmSafe.saveSafe(moneyStorage);
  }

  public List<MoneyPack> take(String currency, int money) throws IOException {

    ArrayList<MoneyPack> moneyList = new ArrayList<>();

    if (!moneyStorage.containsKey(currency)) {
      return new ArrayList<>();
    }

    if (getAmount(currency) < money) {
      return new ArrayList<>();
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
      return moneyList;
    }
    return new ArrayList<>();
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
      String key = entry.getKey();
      ArrayList<MoneyPack> value = entry.getValue();
      if (value.isEmpty()) {
        it.remove();
      }
    }
  }

  public List<MoneyPack> showContent() {
    ArrayList<MoneyPack> wholeList = new ArrayList<>();
    for (ArrayList<MoneyPack> mplist : moneyStorage.values()) {
      for (MoneyPack mp : mplist) {
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
