package cashmachine.atmcommand;

import cashmachine.atm.Atm;

public class PrintCashCommand extends AtmCommand {
  public PrintCashCommand(Atm atm) {
    super(atm);
  }

  public void execute() throws Exception {
    atm.printCash();
  }
}
