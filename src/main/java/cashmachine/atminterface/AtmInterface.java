package cashmachine.atminterface;

import cashmachine.atm.Atm;
import cashmachine.atmcommand.AtmCommand;
import cashmachine.money.MoneyPack;

import java.io.IOException;
import java.util.List;

public abstract class AtmInterface {
  public abstract void showMessage(String message);

  public abstract void showGreeting();

  public abstract void showSuccess();

  public abstract void showHelp() throws IOException;

  public abstract void showError();

  public abstract void showError(String errorText);

  public abstract AtmCommand receiveCommand(Atm atm) throws Exception;

  public abstract void showBalance(List<MoneyPack> money);

  public abstract void giveMoney(List<MoneyPack> money);

}
