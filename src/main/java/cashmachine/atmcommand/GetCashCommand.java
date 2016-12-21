package cashmachine.atmcommand;

import cashmachine.money.MoneyPack;

public class GetCashCommand extends AtmCommand {
  public final String currency;
  public final int amount;

  public GetCashCommand(String currency, int amount) {
    super("getCash");
    this.currency = currency;
    this.amount = amount;
  }
}
