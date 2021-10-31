package ru.geekbrains.chat.client;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextArea mainArea;

    @FXML
    TextField messageField;

    private Network network;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            network = new Network(8189);
            new Thread(() -> {
                try {
                    while (true) {
                        String msg = network.readMsg();
                        mainArea.appendText(msg + "\n");
                    }
                }catch (IOException e){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Connection with server lost");
                    alert.showAndWait();
                }finally {
                    network.close();
                }
            }).start();
        }catch (IOException e){
            throw new RuntimeException("Connection error");
        }
    }
    public void sendMessage(ActionEvent actionEvent) {
        try {
            if (messageField.getText().trim().length() > 0) {
                network.sendMsg(messageField.getText());
                messageField.clear();
                messageField.requestFocus();
            }
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server not available, failed to send message");
            alert.showAndWait();
        }
    }
}