import cashmachine.atm.Atm;
import cashmachine.atm.ConsoleFileAtm;
import cashmachine.atm.TelnetAtm;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Type 1 for console interface; type 2 for telnet server");
    Scanner scanner = new Scanner(System.in);

    String input = scanner.nextLine();
    Atm atm;
    switch (input) {
      case "1":
        atm = new ConsoleFileAtm();
        break;
      case "2":
        System.out.println("Starting server");
        atm = new TelnetAtm();
        break;
      default:
        System.out.println("Incorrect option");
        return;
    }

    atm.start();
  }
}
