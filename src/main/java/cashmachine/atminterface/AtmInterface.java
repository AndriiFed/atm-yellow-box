package cashmachine.atminterface;

public abstract class AtmInterface {
  abstract public void showMessage(String message);
  abstract public void receiveCommand();
}
