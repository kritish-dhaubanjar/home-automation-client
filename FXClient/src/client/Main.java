package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application {

    public static Stage primaryStage;
    public static IPiServer piServer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Home Automation");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

        try {
            Registry registry = LocateRegistry.getRegistry("raspberrypi.local", 1099);
            Main.piServer = (IPiServer) registry.lookup("PISERVER");
        }catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }
    }


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();

        FXReceiver fxReceiver = new FXReceiver();
        fxReceiver.start();

    }

    public void stop() throws Exception {
        super.stop();
    }
}
