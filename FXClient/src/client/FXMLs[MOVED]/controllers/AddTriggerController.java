package client.FXMLs.controllers;

import client.Main;
import client.controllers.TriggerController;
import client.model.Trigger;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

public class AddTriggerController {

    @FXML
    TextField triggerName;
    @FXML
    JFXComboBox<Integer> masterPin, slavePin;
    @FXML
    JFXComboBox<String> masterState, slaveState;


    public void initialize(){
        ObservableList<Integer> master = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10);
        ObservableList<Integer> slave = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10);

        for (Trigger trigger: TriggerController.triggers){
            if(master.contains(trigger.getMasterPin()))
                master.remove(master.indexOf(trigger.getMasterPin()));
            if(slave.contains(trigger.getSlavePin()))
                slave.remove(slave.indexOf(trigger.getSlavePin()));
        }
        masterPin.getItems().setAll(master);
        slavePin.getItems().addAll(slave);
        masterPin.getSelectionModel().selectFirst();
        masterPin.getSelectionModel().selectFirst();

        masterState.getItems().setAll("On", "Off");
        slaveState.getItems().setAll("On", "Off");

        masterState.getSelectionModel().selectFirst();
        slaveState.getSelectionModel().selectFirst();
    }

    public void addTrigger(){
        String triggerName = this.triggerName.getText();
        int masterPin = this.masterPin.getValue();
        int slavePin = this.slavePin.getValue();
        boolean shouldBeState = masterState.getValue().equals("On") ;
        boolean triggerState = slaveState.getValue().equals("On") ;
        try{
            Main.piServer.createTrigger(triggerName,null,masterPin,slavePin,shouldBeState,triggerState);
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
    }



}
