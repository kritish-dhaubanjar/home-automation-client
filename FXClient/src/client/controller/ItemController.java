package client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ItemController {

    @FXML
    GridPane homePane, itemsPane, triggersPane, systemPane, exitPane;

    public void initialize(){

//        refresh = new RefreshItemService();
//        refresh.setOnSucceeded(event -> {
//            itemList = refresh.getValue();
//            reloadItems();
//        });
//        refresh.start();

        exitPane.setOnMouseClicked( event -> Platform.exit() );
        Menu menu = new Menu(homePane, itemsPane, triggersPane, systemPane, exitPane);
        menu.setMenu();

//        icons.put("TV", "tv.png");
//        icons.put("Fan", "fan.png");
//        icons.put("Laptop", "laptop.png");
//        icons.put("Light", "light.png");
//        icons.put("Phone", "phone.png");
//        icons.put("Fridge", "fridge.png");
//        icons.put("Socket", "socket.png");
//        icons.put("Other", "other.png");
    }

}
