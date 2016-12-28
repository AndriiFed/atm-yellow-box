package cashmachine.atm;

import cashmachine.atmcommand.AtmCommand;
import cashmachine.atmcommand.GetCashCommand;
import cashmachine.atmcommand.PutCashCommand;
import cashmachine.atminterface.AtmInterface;
import cashmachine.atminterface.ConsoleInterface;
import cashmachine.atmstorage.ATMStorage;
import cashmachine.exceptions.BadCommandException;
import cashmachine.money.MoneyPack;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class Atm {
  AtmInterface atmInterface;
  ATMStorage atmStorage;
  AtmCommand command;
  private boolean workingState;

  public Atm(AtmInterface atmInterface, ATMStorage atmStorage) {
    this.atmInterface = atmInterface;
    this.atmStorage = atmStorage;
    workingState = true;
  }

  public void start() {
    atmInterface.showGreeting();
    while (workingState) {
      try {
        command = atmInterface.receiveCommand(this);
        command.execute();
      } catch (BadCommandException exception) {
        atmInterface.showError(exception.getMessage());
      } catch (Exception exception) {
        atmInterface.showError();
      }
      command = null;
    }
  }

  public void getCash(String currency, int amount) throws Exception {
    List<MoneyPack> money = atmStorage.take(currency, amount);
    atmInterface.giveMoney(money);
  }

  public void putCash(MoneyPack moneyPack) throws Exception {
    atmStorage.store(moneyPack);
  }

  public void printCash() throws Exception {
    List<MoneyPack> money = atmStorage.showContent();
    atmInterface.showBalance(money);
  }

  public void exit() throws Exception {
    workingState = false;
  }
}
