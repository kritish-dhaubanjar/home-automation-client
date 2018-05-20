package client.database;

import client.model.Item;
import client.model.Trigger;

public class DatabaseTest {
    public static void main(String[] args) {

        Datasource datasource = new Datasource();

        for (Trigger trigger : datasource.triggersQuery()  ) {
            System.out.println(trigger.getName());
        }
    }
}
