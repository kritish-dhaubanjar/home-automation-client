package client.controllers;

import client.FXMLs.controllers.AddItemController;
import client.FXMLs.controllers.EditItemController;
import client.Main;
import client.database.Datasource;
import client.model.Item;
import client.refresh.RefreshItemService;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ItemController {

    @FXML
    GridPane homePane, itemsPane, triggersPane, systemPane, exitPane;
    @FXML
    TilePane tilePane;
    @FXML
    JFXButton addItem;

    public static List<Item> itemList;
    private Service<List<Item>> refresh;
    private Map<String, String> icons = new HashMap<>();

    public void initialize(){

        refresh = new RefreshItemService();
        refresh.setOnSucceeded(event -> {
            itemList = refresh.getValue();
            reloadItems();
        });
        refresh.start();

        exitPane.setOnMouseClicked( event -> Platform.exit() );
        Menu menu = new Menu(homePane, itemsPane, triggersPane, systemPane, exitPane);
        menu.setMenu();

        addItem.setOnAction((event) -> addItem());

        icons.put("tv", "tv.png");
        icons.put("fan", "fan.png");
        icons.put("laptop", "laptop.png");
        icons.put("light", "light.png");
        icons.put("phone", "phone.png");
        icons.put("fridge", "fridge.png");
        icons.put("socket", "socket.png");
        icons.put("other", "other.png");

    }

    private void reloadItems(){

        tilePane.getChildren().clear();

        for(Item item:itemList) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.prefHeight(155);
            anchorPane.prefWidth(135);
            anchorPane.setPadding(new Insets(0, 0,10,10));
            anchorPane.setStyle("-fx-background-color: #FFF;");
            TilePane.setMargin(anchorPane, new Insets(25, 0, 0, 30));

            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setLayoutX(18);
            imageView.setLayoutY(12);

            String icon = "other.png";
            if(icons.containsKey(item.getDeviceName().toLowerCase()))
                icon = icons.get(item.getDeviceName().toLowerCase());

            Image img = new Image("@../../icons/items/" + icon);

            imageView.setImage(img);

            JFXButton delete = new JFXButton("Delete");
            JFXButton edit = new JFXButton("Edit");
            delete.setStyle("-fx-background-color: #DE5B49");
            edit.setStyle("-fx-background-color: #46B29D");
            delete.setLayoutX(10);
            delete.setLayoutY(118);
            edit.setLayoutX(81);
            edit.setLayoutY(118);

            delete.setOnAction((event) ->{
                    try {
                        Main.piServer.deleteItem(item.getGpioPin());
                    }catch (RemoteException e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(e.getMessage());
                        alert.initOwner(homePane.getScene().getWindow());
                        alert.showAndWait();
                    }
                }
            );
            edit.setOnAction(event -> {
                editItem(item);
            });

            anchorPane.getChildren().setAll(imageView, delete, edit);
            tilePane.getChildren().add(anchorPane);
        }
    }

    private void addItem(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(addItem.getScene().getWindow());
        dialog.setResizable(false);
        dialog.setTitle("Add New Item");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../FXMLs/addItemDialog.fxml"));
        try{
            DialogPane dialogPane = fxmlLoader.load();
            dialogPane.getButtonTypes().add(ButtonType.CLOSE);
            dialogPane.getButtonTypes().add(ButtonType.FINISH);
            dialog.setDialogPane(dialogPane);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        Optional<ButtonType> finish = dialog.showAndWait();

        if(finish.isPresent() && finish.get() == ButtonType.FINISH){
            AddItemController controller = fxmlLoader.getController();
            controller.addItem();
        }
    }

    private void editItem(Item item){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(homePane.getScene().getWindow());
        dialog.setResizable(false);
        dialog.setTitle("Edit" + item.getDeviceName());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../FXMLs/editItemDialog.fxml"));
        try{
            DialogPane dialogPane = fxmlLoader.load();
            dialogPane.getButtonTypes().add(ButtonType.CLOSE);
            dialogPane.getButtonTypes().add(ButtonType.FINISH);
            dialog.setDialogPane(dialogPane);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        EditItemController controller = fxmlLoader.getController();
        controller.initialize(item);
        Optional<ButtonType> finish = dialog.showAndWait();

        if(finish.isPresent() && finish.get() == ButtonType.FINISH){
            controller.updateItem(item);
        }
    }

}
