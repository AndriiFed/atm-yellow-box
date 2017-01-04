package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface AtmSafe {

  void saveSafe(HashMap<String, ArrayList<MoneyPack>> safe) throws IOException;

  HashMap<String, ArrayList<MoneyPack>> loadSafe() throws Exception;
}
