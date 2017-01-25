package cashmachine.atm;

import cashmachine.atminterface.ConsoleInterface;
import cashmachine.atminterface.TelnetInterface;
import cashmachine.atmstorage.ATMStorage;

public class ConsoleFileAtm extends Atm {
  public ConsoleFileAtm() throws Exception {
    super(new ConsoleInterface(), new ATMStorage());
  }
}
