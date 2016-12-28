import cashmachine.atm.Atm;
import cashmachine.atm.ConsoleFileAtm;

public class Main {
  public static void main(String[] args) throws Exception {
    Atm atm = new ConsoleFileAtm();
    atm.start();
  }
}
