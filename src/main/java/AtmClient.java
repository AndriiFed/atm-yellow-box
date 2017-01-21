import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class AtmClient {

  public static void main(String[] args) {
    String host = "localhost";
    int port = 1331;
    int connectionTimeout = 10000;
    Socket socket;

    if (args.length >= 2) {
      host = args[0];
      port = Integer.parseInt(args[1]);
    }

    System.out.println("******* ATM Client ver. 1.0 *******");
    System.out.println("args: server port");
    System.out.println("default args: localhost 1331");
    System.out.println("***********************************");
    System.out.println("Connecting to " + host + ":" + port + "...");

    try {
      socket = new Socket();
      SocketAddress socketAddress = new InetSocketAddress(host, port);
      socket.connect(socketAddress, connectionTimeout);

      SendThread sendThread = new SendThread(socket);
      Thread thread = new Thread(sendThread);
      thread.start();

      ReceiveThread receiveThread = new ReceiveThread(socket);
      Thread thread2 = new Thread(receiveThread);
      thread2.start();

    } catch (Exception exc) {
      System.out.println(exc.getMessage());
    }
  }

  static class ReceiveThread implements Runnable {
    private Socket socket = null;

    public ReceiveThread(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      try {
        BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msgReceived;
        while ((msgReceived = receive.readLine()) != null) {
          System.out.println(msgReceived);
        }
      } catch (Exception exc) {
        System.out.println(exc.getMessage());
      }
    }
  }


  static class SendThread implements Runnable {
    private Socket socket = null;

    public SendThread(Socket sock) {
      this.socket = sock;
    }

    public void run() {
      try {
        if (socket.isConnected()) {
          System.out.println("Connected to " + socket.getInetAddress() + " on port " + socket.getPort());
          System.out.println("Type your command to send ATM command. Type '.exit' or '.q' to exit.");
          PrintWriter print = new PrintWriter(socket.getOutputStream(), true);

          while (true) {
            BufferedReader brInput = new BufferedReader(new InputStreamReader(System.in));
            String msgToServerString;
            msgToServerString = brInput.readLine();
            print.println(msgToServerString);
            print.flush();

            if (msgToServerString.equals(".q") || msgToServerString.equals(".exit")) {
              break;
            }
          }
          socket.close();
        }
      } catch (Exception exc) {
        System.out.println(exc.getMessage());
      }
    }
  }

}
