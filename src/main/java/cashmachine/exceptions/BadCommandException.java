package cashmachine.exceptions;

public class BadCommandException extends Exception {
  @Override
  public String getMessage() {
    return "Incorrect command";
  }
}
