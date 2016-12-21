package cashmachine.atminterface;

import cashmachine.atmcommand.AtmCommand;
import cashmachine.exceptions.BadCommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface extends AtmInterface {
  public static final String okMessage = "OK";
  public static final String errorMessage = "ERROR";

  public void showMessage(String message) {
    System.out.println(message);
  }

  public AtmCommand receiveCommand() throws IOException, BadCommandException {
    System.out.print("> ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] input = reader.readLine().split("\\s+");

    return AtmCommand.createCommand(input);
  }
}
