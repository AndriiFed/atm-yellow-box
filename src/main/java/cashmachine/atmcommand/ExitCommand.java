package cashmachine.atmcommand;

import cashmachine.atm.Atm;

public class ExitCommand extends AtmCommand {
  public ExitCommand(Atm atm) {
    super(atm);
  }

  public void execute() throws Exception {
    atm.exit();
  }
}
