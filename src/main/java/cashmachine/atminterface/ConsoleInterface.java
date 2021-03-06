package cashmachine.atminterface;

import cashmachine.atm.Atm;
import cashmachine.atmcommand.AtmCommand;
import cashmachine.exceptions.BadCommandException;
import cashmachine.money.MoneyPack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

public class ConsoleInterface extends AtmInterface {
  public static final String errorMessage = "ERROR";

  public void showMessage(String message) {
    System.out.println(message);
  }

  public void showGreeting() {
    System.out.println("Welcome to Cash machine");
  }

  public void showSuccess() {
    System.out.println("OK");
  }

  @Override
  public void showHelp() throws IOException {
    try {
      InputStream helpStream = getClass().getResourceAsStream("/help.txt");

      if (helpStream == null) {
        throw new FileNotFoundException("help information is not available");
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(helpStream));

      String line;
      while (true) {
        line = reader.readLine();
        if (line == null) {
          break;
        }
        System.out.println(line);
      }

    } catch (FileNotFoundException exception) {
      showMessage(exception.getMessage());
    }

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

  public AtmCommand receiveCommand() throws IOException, BadCommandException {
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

  public void start(Atm atm) throws Exception {
    this.atm = atm;
    workingState = true;
    showGreeting();
    while (workingState) {
      try {
        AtmCommand command = receiveCommand();
        command.execute();
        showSuccess();
      } catch (BadCommandException | IOException exception) {
        showError(exception.getMessage());
      }
    }
  }

  @Override
  public void stop() {
    workingState = false;
  }
}
