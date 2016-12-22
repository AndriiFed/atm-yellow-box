package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import java.util.ArrayList;
import java.util.HashMap;

public class AtmSafeFileByte implements AtmSafe {

  public boolean saveSafe(HashMap<String, ArrayList<MoneyPack>> safe) {
    return true;
  }

  public HashMap<String, ArrayList<MoneyPack>> loadSafe() {
    return null;
  }


}
