package client.controllers;

import client.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

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
                FXMLLoader home = new FXMLLoader();
                home.setLocation(getClass().getResource("home.fxml"));
                Parent root = home.load();
                Main.primaryStage.setScene(new Scene(root,800,600));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));

        itemsPane.setOnMouseClicked((event -> {
            try{
                FXMLLoader items = new FXMLLoader();
                items.setLocation(getClass().getResource("items.fxml"));
                Parent root = items.load();
                Main.primaryStage.setScene(new Scene(root,800,600));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));

        triggersPane.setOnMouseClicked((event -> {
            try{
                FXMLLoader triggers = new FXMLLoader();
                triggers.setLocation(getClass().getResource("triggers.fxml"));
                Parent root = triggers.load();
                Main.primaryStage.setScene(new Scene(root,800,600));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }));


    }

}
