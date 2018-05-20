package client;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class FXReceiver extends Service<Void>{

    private int port = 8085;
    private String host = "224.0.1.2";

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                try{
                    System.out.println("Receiver Listning on 224.0.1.2:8085");
                    byte [] buffer = new byte[10];
                    MulticastSocket socket = new MulticastSocket(port);
                    InetAddress address = InetAddress.getByName(host);
                    socket.joinGroup(address);
                    while (true){
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        System.out.println(new String(buffer, 0, packet.getLength()));

                        Platform.runLater(
                                ()->{
                                String rootId = Main.primaryStage.getScene().getRoot().getId();
                                System.out.println(rootId);

                                try {
                                    Parent root;
                                    switch (rootId) {
                                        case "home":
                                            root = FXMLLoader.load(getClass().getResource("home.fxml"));
                                            Main.primaryStage.setScene(new Scene(root,800,600));
                                            break;
                                        case "items":
                                            root = FXMLLoader.load(getClass().getResource("items.fxml"));
                                            Main.primaryStage.setScene(new Scene(root,800,600));
                                            break;
                                        case "triggers":
                                            root = FXMLLoader.load(getClass().getResource("triggers.fxml"));
                                            Main.primaryStage.setScene(new Scene(root, 800, 600));
                                            break;
                                    }

                                }catch (IOException e){
                                    System.out.println(e.getMessage());
                                }
                            }
                        );
                    }

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                return null;
            }
        };
    }
}
