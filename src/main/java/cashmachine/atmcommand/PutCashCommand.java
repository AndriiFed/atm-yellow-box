package cashmachine.atmcommand;

public class PutCashCommand extends AtmCommand {
  public PutCashCommand(String currency, int value, int amount) {
    super("putCash");
  }
}
