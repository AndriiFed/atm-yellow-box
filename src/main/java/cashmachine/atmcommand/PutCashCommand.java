package cashmachine.atmcommand;

import cashmachine.money.MoneyPack;

public class PutCashCommand extends AtmCommand {
  public final MoneyPack moneyPack;

  public PutCashCommand(MoneyPack moneyPack) {
    super("putCash");
    this.moneyPack = moneyPack;
  }
}
