package client.controllers;

import client.Main;
import client.database.Datasource;
import client.model.Item;
import client.refresh.RefreshItemService;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController{

    @FXML
    GridPane homePane, itemsPane, triggersPane, systemPane, exitPane;
    @FXML
    TilePane tilePane;

    public static Datasource datasource;
    public static List<Item> itemList;
    private Service<List<Item>> refresh;
    private Map<String, String> icons = new HashMap<>();

    private static boolean init=false;

    public void initialize(){
        if(!init) {
            checkDatabase();
            init = true;
        }

        refresh = new RefreshItemService();
        refresh.setOnSucceeded(event -> {
            itemList = refresh.getValue();
            reloadItems();
        });
        refresh.start();

        exitPane.setOnMouseClicked( event -> Platform.exit() );
        Menu menu = new Menu(homePane, itemsPane, triggersPane, systemPane, exitPane);
        menu.setMenu();

        icons.put("tv", "tv.png");
        icons.put("fan", "fan.png");
        icons.put("laptop", "laptop.png");
        icons.put("light", "light.png");
        icons.put("phone", "phone.png");
        icons.put("fridge", "fridge.png");
        icons.put("socket", "socket.png");
        icons.put("other", "other.png");
    }

    private void checkDatabase(){
        datasource = new Datasource();
        if(Datasource.error != null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(Datasource.error);
            alert.showAndWait();
            Platform.exit();
        }
    }

    private void reloadItems(){

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
            Tooltip tooltip = new Tooltip();
            tooltip.setText(item.getNotes());
            Tooltip.install(imageView, tooltip);

            String icon = "other.png";
            if(icons.containsKey(item.getDeviceName().toLowerCase()))
                icon = icons.get(item.getDeviceName().toLowerCase());

            Image img = new Image(getClass().getResourceAsStream("icons/items/"+icon));

            imageView.setImage(img);

            JFXToggleButton button = new JFXToggleButton();
            button.setSelected(item.isState());
            button.setPrefHeight(60);
            button.setPrefWidth(135);
            button.setText(item.getDeviceName());
            button.setLayoutY(95);
            Tooltip.install(button, tooltip);

            button.setOnAction((btnEvent)->{
                try{
                    Main.piServer.setState(item.getGpioPin(), button.isSelected());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
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
