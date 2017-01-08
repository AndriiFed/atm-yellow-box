package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import java.util.ArrayList;
import java.util.HashMap;

public class AtmSafeFileMemory implements AtmSafe {
  private HashMap<String, ArrayList<MoneyPack>> safe = new HashMap<>();

  public void saveSafe(HashMap<String, ArrayList<MoneyPack>> safe) {
    this.safe = safe;
  }

  public HashMap<String, ArrayList<MoneyPack>> loadSafe() {
    return safe;
  }
}
