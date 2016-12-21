package cashmachine.exceptions;

public class CommandValidationException extends Exception {
  @Override
  public String getMessage() {
    return "Incorrect command";
  }
}
