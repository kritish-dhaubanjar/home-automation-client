package client.database;

import client.model.Item;
import client.model.Trigger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    private static Connection connection;
    private static Statement statement;
    private static final String URL = "jdbc:mysql://raspberrypi.local:3306/homeAutomation";
    private static final String username = "pi";
    private static final String password = "raspberry";

    public static boolean success = false;
    public static String error;

    private static final String itemsQuery = "SELECT * FROM items";
    private static final String triggerQuery = "SELECT * FROM triggers";

    public Datasource(){
        try {
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement();
            success = true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        success = false;
    }

    public List<Item> itemsQuery(){
        List<Item> itemsList = new ArrayList<>();
        try {
            ResultSet itemsSet = statement.executeQuery(itemsQuery);
            while (itemsSet.next()){

                int gpioPin = itemsSet.getInt(1);
                String deviceName = itemsSet.getString(2);
                String notes = itemsSet.getString(3);
                boolean state = itemsSet.getBoolean(4);
                int roomId = itemsSet.getInt(5);
                Timestamp timestamp = itemsSet.getTimestamp(6);
                itemsList.add(new Item(gpioPin, deviceName, notes, state, roomId, timestamp.toLocalDateTime()));
            }
            return itemsList;

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return itemsList;
    }

    public List<Trigger> triggersQuery(){
        List<Trigger> triggers = new ArrayList<>();
        try {
            ResultSet triggersSet = statement.executeQuery(triggerQuery);
            while (triggersSet.next()){

                int _id = triggersSet.getInt(1);
                String name = triggersSet.getString(2);
                String note = triggersSet.getString(3);
                boolean shouldBeState = triggersSet.getBoolean(4);
                boolean triggerState = triggersSet.getBoolean(5);
                int masterPin = triggersSet.getInt(6);
                int slavePin = triggersSet.getInt(7);

                triggers.add(new Trigger(_id, name, note, shouldBeState, triggerState, masterPin, slavePin));

            }

            return triggers;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return triggers;
    }


}
