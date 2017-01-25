package cashmachine.atminterface;

import cashmachine.atm.Atm;
import cashmachine.atmcommand.AtmCommand;
import cashmachine.money.MoneyPack;

import java.io.IOException;
import java.util.List;

public abstract class AtmInterface {
  protected Atm atm;
  protected boolean workingState;

  public abstract void start(Atm atm) throws Exception;

  public abstract void stop();

  public abstract void showHelp() throws IOException;

  public abstract void showError(String errorText);

  public abstract void showBalance(List<MoneyPack> money);

  public abstract void giveMoney(List<MoneyPack> money);

}
