package client.FXMLs.controllers;

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

public class EditItemController {

    @FXML
    JFXTextField deviceName, notes;
    @FXML
    JFXComboBox<Integer> gpioPin, roomId;

    public void initialize(Item item){
        ObservableList<Integer> pins = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10);
        for(Item existing: HomeController.itemList){
            if(pins.contains(existing.getGpioPin()) && existing.getGpioPin()!=item.getGpioPin())
                pins.removeAll(existing.getGpioPin());
        }
        roomId.getItems().setAll(1);

        gpioPin.getItems().setAll(pins);
        deviceName.setText(item.getDeviceName());
        notes.setText(item.getNotes());
        gpioPin.getSelectionModel().select(pins.indexOf(item.getGpioPin()));
        roomId.getSelectionModel().select(0);
    }

    public void updateItem(Item item){
        try {
            boolean result = Main.piServer.updateItem(item.getGpioPin(), deviceName.getText(), notes.getText(),
                    gpioPin.getSelectionModel().getSelectedItem(), roomId.getSelectionModel().getSelectedItem());
            System.out.println(result);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

}
