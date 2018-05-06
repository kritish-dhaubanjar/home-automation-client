package client.controller;

import client.database.Datasource;
import client.model.Item;
import client.refresh.RefreshItemService;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController{

    @FXML
    GridPane homePane, itemsPane, triggersPane, systemPane, exitPane;
    @FXML
    TilePane tilePane;

    public static Datasource datasource;
    private static List<Item> itemList;
    private Service<List<Item>> refresh;
    private Map<String, String> icons = new HashMap<>();

    public void initialize(){
        checkDatabase();

        refresh = new RefreshItemService();
        refresh.setOnSucceeded(event -> {
            itemList = refresh.getValue();
            reloadItems();
        });
        refresh.start();

        exitPane.setOnMouseClicked( event -> Platform.exit() );
        Menu menu = new Menu(homePane, itemsPane, triggersPane, systemPane, exitPane);
        menu.setMenu();

        icons.put("TV", "tv.png");
        icons.put("Fan", "fan.png");
        icons.put("Laptop", "laptop.png");
        icons.put("Light", "light.png");
        icons.put("Phone", "phone.png");
        icons.put("Fridge", "fridge.png");
        icons.put("Socket", "socket.png");
        icons.put("Other", "other.png");
    }

    public void checkDatabase(){
        datasource = new Datasource();
        if(datasource.error != null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(datasource.error);
            alert.showAndWait();
            Platform.exit();
        }
    }

    public void reloadItems(){

        tilePane.getChildren().clear();

        for(Item item:itemList) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.prefHeight(155);
            anchorPane.prefWidth(135);
            anchorPane.setStyle("-fx-background-color: #FFF");
            TilePane.setMargin(anchorPane, new Insets(25, 0, 0, 30));

            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setLayoutX(18);
            imageView.setLayoutY(12);

            Image img = new Image("@../../icons/items/" + icons.get(item.getDeviceName()));

            imageView.setImage(img);

            JFXToggleButton button = new JFXToggleButton();
            button.setSelected(item.isState());
            button.setPrefHeight(60);
            button.setPrefWidth(135);
            button.setText(item.getDeviceName());
            button.setLayoutY(95);

            button.setOnAction((btnEvent)->{
                refresh = new RefreshItemService();
                refresh.start();
                refresh.setOnSucceeded(event -> {
                    itemList = refresh.getValue();
                    reloadItems();
                });
            });

            anchorPane.getChildren().setAll(imageView, button);
            tilePane.getChildren().add(anchorPane);
        }
    }
}
