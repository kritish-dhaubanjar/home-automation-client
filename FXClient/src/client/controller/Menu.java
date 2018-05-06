package client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Menu {

    private GridPane homePane, itemsPane, triggersPane, systemPane, exitPane;

    public Menu(GridPane homePane, GridPane itemsPane, GridPane triggersPane, GridPane systemPane, GridPane exitPane) {
        this.homePane = homePane;
        this.itemsPane = itemsPane;
        this.triggersPane = triggersPane;
        this.systemPane = systemPane;
        this.exitPane = exitPane;
    }

    public void setMenu(){
        FXMLLoader fxmlLoader = new FXMLLoader();

        homePane.setOnMouseClicked((event -> {
            try {
                fxmlLoader.setLocation(getClass().getResource("../home.fxml"));
                homePane.getScene().setRoot(fxmlLoader.load());
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));

        itemsPane.setOnMouseClicked((event -> {
            try {
                fxmlLoader.setLocation(getClass().getResource("../items.fxml"));
                itemsPane.getScene().setRoot(fxmlLoader.load());
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));


    }

}
