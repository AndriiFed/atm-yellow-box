package cashmachine.atmstorage;

import cashmachine.money.MoneyPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;


public class AtmSafeH2Db implements AtmSafe {
  private String dbName = "jdbc:h2:~/atmdb";
  private String dbUser = "sa";
  private String dbPassword = "sa";

  public AtmSafeH2Db() {

  }

  public AtmSafeH2Db(String dbName) {
    this.dbName = dbName;
  }

  public void saveSafe(HashMap<String, ArrayList<MoneyPack>> safe) throws Exception {
    ArrayList<MoneyPack> wholeList = new ArrayList<>();
    if (!safe.isEmpty()) {
      for (ArrayList<MoneyPack> mpList : safe.values()) {
        for (MoneyPack mp : mpList) {
          wholeList.add(mp);
        }
      }
    } else {
      return;
    }

    try {
      Class.forName("org.h2.Driver");
      Connection conn = DriverManager.getConnection(dbName, dbUser, dbPassword);
      Statement st;
      st = conn.createStatement();

      String sqlStr = "DROP TABLE IF EXISTS atm;";
      st.execute(sqlStr);

      sqlStr = "CREATE TABLE IF NOT EXISTS atm(id BIGINT PRIMARY KEY AUTO_INCREMENT, "
        + "currency CHAR(3) NOT NULL, value BIGINT NOT NULL, amount BIGINT NOT NULL);";
      st.execute(sqlStr);

      for (MoneyPack moneyPack : wholeList) {
        sqlStr = String.format("INSERT INTO atm(currency, value, amount) VALUES('%s', %d, %d);",
          moneyPack.getCurrency(), moneyPack.getValue(), moneyPack.getAmount());
        st.execute(sqlStr);
      }

      st.close();
      conn.close();
    } catch (SQLException sqlException) {
      System.out.println("SQL Error: " + sqlException);
    }

  }

  public HashMap<String, ArrayList<MoneyPack>> loadSafe() throws Exception {
    ATMStorage moneyStorage = new ATMStorage("memory");
    moneyStorage.emptyStorage();

    try {
      Class.forName("org.h2.Driver");
      Connection conn = DriverManager.getConnection(dbName, dbUser, dbPassword);
      Statement st;
      st = conn.createStatement();

      ResultSet resultSet = conn.getMetaData().getTables(null, null, "ATM", null);
      if (!resultSet.next()) {
        System.out.println("Table ATM does not exist. Creating.");
        saveSafe(moneyStorage.getMoneyStorage());
        return moneyStorage.getMoneyStorage();
      }

      String sqlStr = "SELECT * FROM atm";
      ResultSet result;
      result = st.executeQuery(sqlStr);
      while (result.next()) {
        //int id = result.getInt("id");
        String currency = result.getString("currency");
        int value = result.getInt("value");
        int amount = result.getInt("amount");
        //System.out.println(id + " " + currency + " " + value + " " + amount);
        moneyStorage.store(new MoneyPack(currency, value, amount));
      }
      result.close();
      st.close();
      conn.close();
    } catch (SQLException sqlException) {
      System.out.println("SQL Error: " + sqlException);
    }
    return moneyStorage.getMoneyStorage();
  }
}
