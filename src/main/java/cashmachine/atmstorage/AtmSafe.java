package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface AtmSafe {

  void saveSafe(HashMap<String, ArrayList<MoneyPack>> safe) throws Exception;

  HashMap<String, ArrayList<MoneyPack>> loadSafe() throws Exception;
}
