package client.controllers;

import client.controllers.FXMLs.controllers.AddTriggerController;
import client.controllers.FXMLs.controllers.EditTriggerController;
import client.Main;
import client.model.Item;
import client.model.Trigger;
import client.refresh.RefreshTriggerService;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public class TriggerController {

    @FXML
    GridPane exitPane, homePane, itemsPane, triggersPane, systemPane;
    @FXML
    TilePane tilePane;
    @FXML
    JFXButton addTrigger;

    private Service<List<Trigger>> refresh;
    public static List<Trigger> triggers;

    public void initialize(){

        refresh = new RefreshTriggerService();
        refresh.setOnSucceeded(event -> {
            triggers = refresh.getValue();
            reloadTriggers();
        });
        refresh.start();

        exitPane.setOnMouseClicked( event -> Platform.exit() );
        addTrigger.setOnMouseClicked(event -> showAddTriggerDialog());
        Menu menu = new Menu(homePane, itemsPane, triggersPane, systemPane, exitPane);
        menu.setMenu();
    }

    private void reloadTriggers(){
        tilePane.getChildren().clear();

        for(Trigger trigger:triggers) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.prefHeight(155);
            anchorPane.prefWidth(135);
            anchorPane.setPadding(new Insets(0, 0,10,10));
            anchorPane.setStyle("-fx-background-color: #FFF;");
            TilePane.setMargin(anchorPane, new Insets(25, 0, 0, 30));

            ImageView imageView = new ImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setLayoutX(45);
            imageView.setLayoutY(12);

//            ImageView info = new ImageView(new Image("@../../icons/info.png"));
            ImageView info = new ImageView(new Image(getClass().getResourceAsStream("icons/info.png")));
            info.setFitWidth(25);
            info.setFitHeight(25);
            info.setCursor(Cursor.HAND);
            info.setLayoutY(10);
            info.setLayoutX(10);

            info.setOnMouseClicked((event) ->
                triggerInfo(trigger.getMasterPin(), trigger.getSlavePin(),
                        trigger.isShouldBeState(), trigger.isTriggerState())
            );

            String icon = "other.png";

            Image img = new Image(getClass().getResourceAsStream("icons/items/"+icon));

            imageView.setImage(img);

            Text name = new Text(trigger.getName());
            name.setLayoutY(100);
            name.setLayoutX(20);

            JFXButton delete = new JFXButton("Delete");
            JFXButton edit = new JFXButton("Edit");
            delete.setStyle("-fx-background-color: #DE5B49");
            edit.setStyle("-fx-background-color: #46B29D");
            delete.setLayoutX(10);
            delete.setLayoutY(118);
            edit.setLayoutX(81);
            edit.setLayoutY(118);

            delete.setOnAction((event) -> {
                System.out.println(trigger.get_id());
                try{
                    Main.piServer.deleteTrigger(trigger.get_id());
                }catch (RemoteException e){
                    System.out.println(e.getMessage());
                }
            });

            edit.setOnAction(event->editTrigger(trigger));

            anchorPane.getChildren().setAll(imageView, info, delete, edit, name);
            tilePane.getChildren().add(anchorPane);
        }
    }

    private void triggerInfo(int masterPin, int slavePin, boolean shouldBeState, boolean triggerState){
        String masterState = shouldBeState ? "on" : "off";
        String slaveState = triggerState ? "on" : "off";
        String info = "When " + getName(masterPin) + " turns " + masterState + ", " + getName(slavePin) + " turns " + slaveState + ".";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Trigger Information");
        alert.setHeaderText(info);
        alert.showAndWait();

    }

    private static String getName(int gpioPin){
        for(Item item : HomeController.itemList){
            if(item.getGpioPin() == gpioPin)
                return item.getDeviceName();
        }
        return "null";
    }

    private void editTrigger(Trigger trigger){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(tilePane.getScene().getWindow());
        dialog.setResizable(false);
        dialog.setTitle("Edit " + trigger.getName());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXMLs/editTriggerDialog.fxml"));
        try{
            DialogPane pane = fxmlLoader.load();
            pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.setDialogPane(pane);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        EditTriggerController controller = fxmlLoader.getController();
        controller.initialize(trigger);
        Optional<ButtonType> action = dialog.showAndWait();

        if(action.isPresent() && action.get().equals(ButtonType.OK)){
            controller.updateTrigger(trigger);
        }
    }

    private void showAddTriggerDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        ButtonType add = new ButtonType("Add",ButtonBar.ButtonData.OK_DONE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLs/addTriggerDialog.fxml"));
        try {
            dialog.setDialogPane(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        dialog.getDialogPane().getButtonTypes().addAll(add, ButtonType.CANCEL);
        Optional<ButtonType> option = dialog.showAndWait();
        if(option.isPresent() && option.get().equals(add)){
            AddTriggerController controller = fxmlLoader.getController();
            controller.addTrigger();
        }
    }

}
