import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AtmGuiClient extends Application {
  private String host = "localhost";
  private int port = 1331;
  private int connectionTimeout = 10000;
  private Socket socket = new Socket();
  private TextField textInput;
  private TextArea textArea;

  public void sendCommand(String msgToServerString) {

    try {
      if (socket.isConnected()) {
        PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
        print.println(msgToServerString);
        print.flush();
      } else {
        System.out.println("Not connected to server.");
      }
    } catch (Exception exc) {
      System.out.println(exc.getMessage());
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    //Parent root = FXMLLoader.load(getClass().getResource("atmclient.fxml"));
    primaryStage.setTitle("ATM Client");
    primaryStage.setResizable(false);

    VBox vboxPane = new VBox(20);
    vboxPane.setAlignment(Pos.TOP_CENTER);
    vboxPane.setPadding(new Insets(20, 20, 20, 20));
    vboxPane.setStyle("-fx-background-color: yellow;");
    Scene myScene = new Scene(vboxPane, 500, 500);
    primaryStage.setScene(myScene);

    Label myLabel = new Label("ATM GUI YELLOW BOX client");
    myLabel.setPadding(new Insets(20, 0, 0, 0));
    myLabel.setStyle("-fx-font: 16px Tahoma; -fx-text-fill: blue;");
    vboxPane.getChildren().add(myLabel);

    textArea = new TextArea();
    textArea.setStyle("-fx-font: 12px Tahoma; -fx-background-color: yellow;");
    textArea.setEditable(false);
    textArea.setPrefWidth(450);
    textArea.setPrefHeight(350);
    textArea.setWrapText(false);
    vboxPane.getChildren().add(textArea);

    HBox hboxPane = new HBox(10);
    hboxPane.setAlignment(Pos.CENTER);
    hboxPane.setStyle("-fx-background-color: yellow;");
    vboxPane.getChildren().add(hboxPane);

    textInput = new TextField("");
    textInput.setPrefWidth(400);
    textInput.setPrefHeight(30);
    textInput.setStyle("-fx-font: 14px Tahoma;");
    textInput.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent ke) {
          if (ke.getCode().equals(KeyCode.ENTER)) {
            String message = textInput.getText();
            if (!message.equals("")) {
              sendCommand(message);
              textInput.clear();
              textArea.appendText(message + "\n");
              System.out.println("COMMAND: " + message);
            }
          }
        }
      });

    hboxPane.getChildren().add(textInput);
    Button sendButton = new Button("SEND");
    sendButton.setPrefHeight(30);
    sendButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override public void handle(ActionEvent event) {
        String message = textInput.getText();
        if (!message.equals("")) {
          sendCommand(message);
          textInput.clear();
          textArea.appendText(message + "\n");
          System.out.println("COMMAND: " + message);
        }
      }
    });

    sendButton.setDisable(true);
    textInput.setDisable(true);
    hboxPane.getChildren().add(sendButton);
    primaryStage.show();

    InetSocketAddress address = new InetSocketAddress(host, port);

    try {
      if (!socket.isConnected()) {
        socket.connect(address, connectionTimeout);
        System.out.println("Connected to " + socket.getInetAddress() + " on port " + socket.getPort());
        sendButton.setDisable(false);
        textInput.setDisable(false);
        textArea.appendText("Connected to " + host + ":" + port + "\n");
        textInput.requestFocus();
      }
    } catch (IOException exc) {
      Alert alert = new Alert(Alert.AlertType.ERROR, host + ":" + port + "\r\n" + exc.getMessage());
      alert.showAndWait();
      System.out.println(exc.getMessage());
      Platform.exit();
    }
  }

  public class SendButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      System.out.println(event.toString());
      //textArea.appendText(textInput.getText());
      //textInput.clear();
      textInput.appendText("send");
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
