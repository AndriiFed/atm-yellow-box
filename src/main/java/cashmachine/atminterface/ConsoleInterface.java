package cashmachine.atminterface;

import cashmachine.atm.Atm;
import cashmachine.atmcommand.AtmCommand;
import cashmachine.exceptions.BadCommandException;
import cashmachine.money.MoneyPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleInterface extends AtmInterface {
  public static final String okMessage = "OK";
  public static final String errorMessage = "ERROR";

  public void showMessage(String message) {
    System.out.println(message);
  }

  @Override
  public void showGreeting() {
    System.out.println("HELLO");
  }

  @Override
  public void showError() {
    System.out.println(errorMessage);
  }

  @Override
  public void showError(String errorText) {
    System.out.println(errorMessage + ": " + errorText);
  }

  @Override
  public void showBalance(List<MoneyPack> money) {
    if (money.size() == 0) {
      showMessage("EMPTY");
    }
    for (MoneyPack mp : money) {
      System.out.println(mp);
    }
  }

  @Override
  public void giveMoney(List<MoneyPack> money) {
    if (money.size() == 0) {
      showMessage("INSUFFICIENT FUNDS");
    }
    for (MoneyPack mp : money) {
      System.out.println(mp);
    }
  }

  public AtmCommand receiveCommand(Atm atm) throws IOException, BadCommandException {
    System.out.print("> ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] input = reader.readLine().split("\\s+");

    return AtmCommand.createCommand(atm, input);
  }
}
