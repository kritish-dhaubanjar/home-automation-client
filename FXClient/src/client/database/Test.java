package client.database;

import client.model.Item;

public class Test {
    public static void main(String[] args) {

        Datasource datasource = new Datasource();

        for (Item item : datasource.itemsQuery()  ) {
            System.out.println(item.getDeviceName());
        }
    }
}
