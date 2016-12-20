package cashmachine.atmcommand;

public abstract class AtmCommand {
  private String atmMethod;

  protected AtmCommand(String method) {
    atmMethod = method;
  }

  public static AtmCommand createCommand(String... args) {
    if ( ! validateCommand(args)) return null;

    for (String s: args) {
      System.out.println(s);
    }
    return null;
  }

  public String getMethod() {
    return atmMethod;
  }

  private static boolean validateCommand(String... args) {
    if (args.length == 0) {
      return false;
    }
    if (!args[0].contains("+") || !args[0].contains("-") || !args[0].contains("?")) {
      return false;
    }

    return true;
  }
}
