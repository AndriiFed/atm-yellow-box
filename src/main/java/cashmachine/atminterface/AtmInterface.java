package cashmachine.atminterface;

public abstract class AtmInterface {
  public abstract void showMessage(String message);

  public abstract void receiveCommand();
}
