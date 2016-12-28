package cashmachine.atmcommand;

import cashmachine.atm.Atm;
import cashmachine.money.MoneyPack;

public class GetCashCommand extends AtmCommand {
  public final String currency;
  public final int amount;

  public GetCashCommand(Atm atm, String currency, int amount) {
    super(atm);
    this.currency = currency;
    this.amount = amount;
  }

  public void execute() throws Exception {
    atm.getCash(currency, amount);
  }
}
