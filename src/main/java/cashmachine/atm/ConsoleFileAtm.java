package cashmachine.atm;

import cashmachine.atminterface.ConsoleInterface;

public class ConsoleFileAtm extends Atm {
  public ConsoleFileAtm() {
    super(new ConsoleInterface());
  }
}
