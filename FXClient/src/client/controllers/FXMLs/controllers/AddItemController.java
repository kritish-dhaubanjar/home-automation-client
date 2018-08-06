package client.controllers.FXMLs.controllers;

import client.Main;
import client.controllers.HomeController;
import client.model.Item;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;

public class AddItemController {

    @FXML
    JFXTextField deviceName, notes;
    @FXML
    JFXComboBox<Integer> gpioPin, roomId;

    public void initialize(){
        ObservableList<Integer> pins = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10);
        for(Item item: HomeController.itemList){
            if(pins.contains(item.getGpioPin()))
                pins.removeAll(item.getGpioPin());
        }
        gpioPin.getItems().setAll(pins);
        roomId.getItems().add(1);
        gpioPin.getSelectionModel().select(0);
        roomId.getSelectionModel().select(0);
    }

    public void addItem(){
        try {
            boolean result = Main.piServer.createItem(false, deviceName.getText(), notes.getText(),
                    gpioPin.getSelectionModel().getSelectedItem(), roomId.getSelectionModel().getSelectedItem());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Item added Successfully!");
                alert.showAndWait();
        }catch (RemoteException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

}
