package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;
import cashmachine.money.MoneyPackSortByValueDesc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ATMStorage {
  private AtmSafe atmSafe = new AtmSafeFileByte();
  private HashMap<String, ArrayList<MoneyPack>> moneyStorage = new HashMap<>(); // = atmSafe.loadSafe();

  public void store(MoneyPack moneyPack) {

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
  }

  public List<MoneyPack> take() {
    return null;
  }

  public void showContent() {
    for (ArrayList<MoneyPack> mplist : moneyStorage.values()) {
      System.out.println(mplist);
    }
  }

  public HashMap<String, ArrayList<MoneyPack>> getMoneyStorage() {
    return moneyStorage;
  }

}
