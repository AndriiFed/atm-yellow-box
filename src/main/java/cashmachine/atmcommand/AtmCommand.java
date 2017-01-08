package cashmachine.atmcommand;

import cashmachine.atm.Atm;
import cashmachine.exceptions.BadCommandException;
import cashmachine.money.MoneyPack;

public abstract class AtmCommand {
  public final Atm atm;

  protected AtmCommand(Atm atm) {
    this.atm = atm;
  }

  public abstract void execute() throws Exception;

  public static AtmCommand createCommand(Atm atm, String[] args) throws BadCommandException {
    if ( ! validateCommand(args)) {
      throw new BadCommandException();
    }

    switch (args[0]) {
      case "+":
        MoneyPack pack = new MoneyPack(args[1].toUpperCase(), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        return new PutCashCommand(atm, pack);
      case "-":
        String currency = args[1].toUpperCase();
        int amount = Integer.parseInt(args[2]);
        return new GetCashCommand(atm, currency, amount);
      case "?":
        return new PrintCashCommand(atm);
      case "exit":
      case "q":
        return new ExitCommand(atm);
      case "help":
        return new HelpCommand(atm);
      default:
        throw new BadCommandException();
    }

  }

  private static boolean validateCommand(String[] args) {
    if (args.length == 0) {
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
        default:
          if (args.length != 1) {
            return false;
          }
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
