package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import java.util.ArrayList;
import java.util.HashMap;

public interface AtmSafe {

  boolean saveSafe(HashMap<String, ArrayList<MoneyPack>> safe);

  HashMap<String, ArrayList<MoneyPack>> loadSafe();
}
