package cashmachine.atmcommand;

import cashmachine.atm.Atm;
import cashmachine.money.MoneyPack;

public class PutCashCommand extends AtmCommand {
  public final MoneyPack moneyPack;

  public PutCashCommand(Atm atm, MoneyPack moneyPack) {
    super(atm);
    this.moneyPack = moneyPack;
  }

  @Override
  public void execute() throws Exception {
    atm.putCash(moneyPack);
  }
}
