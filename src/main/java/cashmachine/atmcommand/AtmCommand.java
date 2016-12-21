package cashmachine.atmcommand;

import cashmachine.exceptions.BadCommandException;
import cashmachine.money.MoneyPack;

public abstract class AtmCommand {
  public final String atmMethod;

  protected AtmCommand(String method) {
    atmMethod = method;
  }

  public static AtmCommand createCommand(String[] args) throws BadCommandException {
    if ( ! validateCommand(args)) {
      throw new BadCommandException();
    }

    switch (args[0]) {
      case "+":
        MoneyPack pack = new MoneyPack(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        return new PutCashCommand(pack);
      case "-":
        String currency = args[1];
        int amount = Integer.parseInt(args[2]);
        return new GetCashCommand(currency, amount);
      case "?":
        return new PrintCashCommand();
      case "exit":
        return new ExitCommand();
      default:
        throw new BadCommandException();
    }

  }

  private static boolean validateCommand(String[] args) {
    if (args.length == 0) {
      return false;
    }

    if (!args[0].contains("+") && !args[0].contains("-") && !args[0].contains("?") && !args[0].contains("exit")) {
      return false;
    }

    try {
      switch (args[0]) {
        case "+":
          if (args.length != 4 || args[1].length() != 3) {
            return false;
          }
          int value = Integer.parseInt(args[2]);
          if (value <= 0 || Integer.parseInt(args[3]) <= 0) {
            return false;
          }
          if (!validateDenomination(value)) {
            return false;
          }
          break;
        case "-":
          if (args.length != 3 || args[1].length() != 3) {
            return false;
          }
          if (Integer.parseInt(args[2]) <= 0) {
            return false;
          }
          break;
        case "?":
        case "exit":
          if (args.length != 1) {
            return false;
          }
          break;
        default:
          return false;
      }
    } catch (NumberFormatException exception) {
      return false;
    }

    return true;
  }

  /*
  Permitted denominations: 1 5 10 50 100 500 1000 5000
   */
  private static boolean validateDenomination(int value) {
    int denomination;
    for (int i = 0; i <= 3; i++) {
      denomination = (int)Math.pow(10, i);
      if (value == denomination || value == denomination * 5) {
        return true;
      }
    }
    return false;
  }
}
