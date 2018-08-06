package client.FXMLs.controllers;

import client.Main;
import client.model.Trigger;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;

public class EditTriggerController {

    @FXML
    JFXTextField name;
    @FXML
    JFXComboBox<String> shouldBeState,triggerState;
    @FXML
    JFXComboBox<Integer> masterPin,slavePin;

    private ObservableList<Integer> masterPins = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10);
    private ObservableList<Integer> slavePins = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10);

    public void initialize(Trigger trigger){
        this.name.setText(trigger.getName());
        this.shouldBeState.getItems().setAll("On","Off");
        this.triggerState.getItems().setAll("On","Off");

        this.slavePins.remove(trigger.getMasterPin());
        this.masterPins.remove(trigger.getSlavePin());

        this.masterPin.getItems().setAll(masterPins);
        this.slavePin.getItems().setAll(slavePins);

        this.shouldBeState.getSelectionModel().select(trigger.isShouldBeState() ? "On" : "Off");
        this.triggerState.getSelectionModel().select(trigger.isTriggerState() ? "On" : "Off");
        this.masterPin.getSelectionModel().select(masterPins.indexOf(trigger.getMasterPin()));
        this.slavePin.getSelectionModel().select(slavePins.indexOf(trigger.getSlavePin()));
    }

    public void updateTrigger(Trigger trigger){
        String newName = name.getText();
        boolean newShouldBeState = shouldBeState.getValue().equals("On");
        boolean newTriggerState = triggerState.getValue().equals("On");
        try {
            boolean success = Main.piServer.updateTrigger(trigger.get_id(), newName, null,
                    trigger.getMasterPin(), trigger.getSlavePin(), newShouldBeState, newTriggerState);
            Alert alert = new Alert( !success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            if (!success){
                alert.setTitle("Hurray!");
                alert.setHeaderText("Trigger " + trigger.getName() + " updated successfully!");
            }else{
                alert.setTitle("Oh Snap!");
                alert.setHeaderText("Something went wrong, A closed chain of Trigger was created !");
            }
            alert.showAndWait();
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }

    }

}
