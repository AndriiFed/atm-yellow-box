package cashmachine.atminterface;

import cashmachine.atm.Atm;
import cashmachine.atmcommand.AtmCommand;
import cashmachine.exceptions.BadCommandException;
import cashmachine.money.MoneyPack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TelnetInterface extends AtmInterface {
  private Socket connectionField;
  private volatile String output = "";
  private Object lock = new Object();

  private Runnable sessionTask = () -> {
    try {
      final Socket connection = connectionField;
      OutputStream out = connection.getOutputStream();
      InputStream in = connection.getInputStream();
      Writer writer = new OutputStreamWriter(out);
      writer.write("Welcome to Cash machine\n");
      while (true) {
        writer.write(">");
        writer.flush();
        try {
          AtmCommand command = AtmCommand.createCommand(atm, getInput(in));
          synchronized (lock) {
            command.execute();
            if (output.length() > 0) {
              if (output == "EXIT") {
                break;
              }
              writer.write(output);
              writer.flush();
              output = "";
            }
          }
        } catch (BadCommandException exception) {
          writer.write(exception.getMessage() + "\n");
          writer.flush();
        } catch (Exception exception) {
          System.out.println(exception.getMessage());
          writer.write(exception.getMessage() + "\n");
          writer.flush();
        }

      }
      connection.close();
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
    }
  };

  @Override
  public void start(Atm atm) throws Exception {
    this.atm = atm;
    workingState = true;
    ExecutorService service = Executors.newFixedThreadPool(2);
    try (ServerSocket socket = new ServerSocket(1331)) {
      while (workingState) {
        connectionField = socket.accept();
        service.submit(sessionTask);
      }
      connectionField.close();
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
    }


  }

  @Override
  public void stop() {
    output = "EXIT";
  }


  @Override
  public void showHelp() throws IOException {
    try {
      InputStream helpStream = getClass().getResourceAsStream("/help.txt");

      if (helpStream == null) {
        throw new FileNotFoundException("help information is not available");
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(helpStream));

      String line;
      while (true) {
        line = reader.readLine();
        if (line == null) {
          break;
        }
        output += line + "\n";
      }

    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void showError(String errorText) {
  }

  public String[] getInput(InputStream in) throws IOException {
    String line;
    while (true) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      line = reader.readLine();
      if ( ! line.isEmpty() ) {
        break;
      }
    }
    return line.split("\\s+");
  }

  @Override
  public void showBalance(List<MoneyPack> money) {
    if (money.size() == 0) {
      output = "EMPTY\n";
      return;
    }
    String result = "";
    for (MoneyPack mp : money) {
      result += mp + "\n";
    }
    output = result;
  }

  @Override
  public void giveMoney(List<MoneyPack> money) {
    if (money.size() == 0) {
      output = "INSUFFICIENT FUNDS\n\n";
      return;
    }
    String result = "";
    for (MoneyPack mp : money) {
      result += mp + "\n";
    }

    output = result;
  }


}
