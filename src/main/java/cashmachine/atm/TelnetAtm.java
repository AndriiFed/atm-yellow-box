package cashmachine.atm;

import cashmachine.atminterface.TelnetInterface;
import cashmachine.atmstorage.ATMStorage;

public class TelnetAtm extends Atm {
  public TelnetAtm() throws Exception {
    super(new TelnetInterface(), new ATMStorage());
  }
}
