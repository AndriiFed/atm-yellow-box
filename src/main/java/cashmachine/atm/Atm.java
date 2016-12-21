package cashmachine.atm;

import cashmachine.atmcommand.AtmCommand;
import cashmachine.atmcommand.GetCashCommand;
import cashmachine.atmcommand.PutCashCommand;
import cashmachine.atminterface.AtmInterface;
import cashmachine.atminterface.ConsoleInterface;
import cashmachine.exceptions.BadCommandException;

import java.io.IOException;
import java.lang.reflect.Method;

public abstract class Atm {
  AtmInterface atmInterface;
  AtmCommand command;
  private boolean workingState;

  public Atm(AtmInterface atmInterface) {
    this.atmInterface = atmInterface;
    workingState = true;
  }

  public void start() {
    while (workingState) {
      try {
        command = atmInterface.receiveCommand();
        Method method = this.getClass().getMethod(command.atmMethod);
        method.invoke(this);
      } catch (BadCommandException exception) {
        atmInterface.showMessage(ConsoleInterface.errorMessage + ": " + exception.getMessage());
      } catch (Exception exception) {
        atmInterface.showMessage(ConsoleInterface.errorMessage);
      }
      command = null;
    }
  }

  public void getCash() throws BadCommandException {
    if (command == null) {
      throw new BadCommandException();
    }
    GetCashCommand getCashCommand = (GetCashCommand) command;
    System.out.println("get " + getCashCommand.currency + " " + getCashCommand.amount);
  }

  public void putCash() throws BadCommandException {
    if (command == null) {
      throw new BadCommandException();
    }
    PutCashCommand putCashCommand = (PutCashCommand) command;
    System.out.println("put " + putCashCommand.moneyPack);
  }

  public void printCash() throws BadCommandException {
    if (command == null) {
      throw new BadCommandException();
    }
    System.out.println("print cash");
  }

  public void exit() {
    workingState = false;
  }
}
