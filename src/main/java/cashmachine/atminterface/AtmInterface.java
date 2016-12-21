package cashmachine.atminterface;

import cashmachine.atmcommand.AtmCommand;

public abstract class AtmInterface {
  public abstract void showMessage(String message);

  public abstract AtmCommand receiveCommand() throws Exception;
}
