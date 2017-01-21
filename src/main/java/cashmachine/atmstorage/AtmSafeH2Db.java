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
    Class.forName("org.h2.Driver");
    Connection conn = DriverManager.getConnection(dbName, dbUser, dbPassword);
    Statement st = null;
    ResultSet resultSet = null;
    String sqlStr;

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
      st = conn.createStatement();
      resultSet = conn.getMetaData().getTables(null, null, "ATM", null);

      if (resultSet.next()) {
        //System.out.println("Table ATM found! Deleting old records.");
        sqlStr =  "DELETE FROM atm;";
        st.execute(sqlStr);
      }

      sqlStr = "CREATE TABLE IF NOT EXISTS atm(id BIGINT PRIMARY KEY AUTO_INCREMENT, "
        + "currency CHAR(3) NOT NULL, value BIGINT NOT NULL, amount BIGINT NOT NULL);";
      st.execute(sqlStr);

      for (MoneyPack moneyPack : wholeList) {
        sqlStr = String.format("INSERT INTO atm(currency, value, amount) VALUES('%s', %d, %d);",
          moneyPack.getCurrency(), moneyPack.getValue(), moneyPack.getAmount());
        st.execute(sqlStr);
      }
    } catch (SQLException sqlException) {
      System.out.println("SQL Error: " + sqlException);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (st != null) {
        st.close();
      }
      if (conn != null) {
        conn.close();
      }
    }

  }

  public HashMap<String, ArrayList<MoneyPack>> loadSafe() throws Exception {
    ATMStorage moneyStorage = new ATMStorage("memory");
    moneyStorage.emptyStorage();
    Class.forName("org.h2.Driver");
    Connection conn = DriverManager.getConnection(dbName, dbUser, dbPassword);
    Statement st = null;
    ResultSet resultSet = null;
    String sqlStr;

    try {
      st = conn.createStatement();
      resultSet = conn.getMetaData().getTables(null, null, "ATM", null);

      if (!resultSet.next()) {
        //System.out.println("Table ATM does not exist. Creating.");
        saveSafe(moneyStorage.getMoneyStorage());
        return moneyStorage.getMoneyStorage();
      }

      sqlStr = "SELECT * FROM atm";
      resultSet = st.executeQuery(sqlStr);
      while (resultSet.next()) {
        //int id = resultSet.getInt("id");
        String currency = resultSet.getString("currency");
        int value = resultSet.getInt("value");
        int amount = resultSet.getInt("amount");
        //System.out.println(id + " " + currency + " " + value + " " + amount);
        moneyStorage.store(new MoneyPack(currency, value, amount));
      }
    } catch (SQLException sqlException) {
      System.out.println("SQL Error: " + sqlException);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (st != null) {
        st.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    return moneyStorage.getMoneyStorage();
  }
}
