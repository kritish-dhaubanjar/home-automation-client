package client.controllers;

import client.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        homePane.setOnMouseClicked((event -> {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("../home.fxml"));
                Main.primaryStage.setScene(new Scene(root,800,600));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));

        itemsPane.setOnMouseClicked((event -> {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("../items.fxml"));
                Main.primaryStage.setScene(new Scene(root,800,600));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));

        triggersPane.setOnMouseClicked((event -> {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("../triggers.fxml"));
                Main.primaryStage.setScene(new Scene(root,800,600));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));


    }

}
