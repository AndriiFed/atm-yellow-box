package cashmachine.atminterface;

public class ConsoleInterface extends AtmInterface {
  public void showMessage(String message) {
    System.out.println(message);
  }

  public void receiveCommand() {

  }
}
