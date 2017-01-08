package cashmachine.atminterface;

import cashmachine.atm.Atm;
import cashmachine.atmcommand.AtmCommand;
import cashmachine.exceptions.BadCommandException;
import cashmachine.money.MoneyPack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;

public class ConsoleInterface extends AtmInterface {
  public static final String errorMessage = "ERROR";

  public void showMessage(String message) {
    System.out.println(message);
  }

  @Override
  public void showGreeting() {
    System.out.println("Welcome to Cash machine");
  }

  @Override
  public void showSuccess() {
    System.out.println("OK");
  }

  @Override
  public void showHelp() throws IOException {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("help.txt"));

      String line;
      while (true) {
        line = reader.readLine();
        if (line == null) {
          break;
        }
        System.out.println(line);
      }

    } catch (FileNotFoundException exception) {
      showError("help information is not available");
    }

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
    String line;
    while (true) {
      System.out.print("> ");
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      line = reader.readLine();
      if ( ! line.isEmpty() ) {
        break;
      }
    }
    String[] input = line.split("\\s+");

    return AtmCommand.createCommand(atm, input);
  }
}
