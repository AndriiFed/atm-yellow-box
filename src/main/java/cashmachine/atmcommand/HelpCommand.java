package cashmachine.atmcommand;

import cashmachine.atm.Atm;

public class HelpCommand extends AtmCommand {
  public HelpCommand(Atm atm) {
    super(atm);
  }

  public void execute() throws Exception {
    atm.help();
  }
}
