package cashmachine.money;

import java.io.Serializable;

public class MoneyPack implements Serializable {
  private String currency;
  private int value;
  private int amount;

  public MoneyPack(String currency, int value, int amount) {
    this.currency = currency;
    this.value = value;
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public int getValue() {
    return value;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return (String) currency + " " + value + " " + amount;
  }

}
