package cashmachine.money;

import java.util.Comparator;

public class MoneyPackSortByValueDesc implements Comparator<MoneyPack> {

  public int compare(MoneyPack mp1, MoneyPack mp2) {
    return Integer.compare(mp2.getValue(), mp1.getValue());
  }
}
