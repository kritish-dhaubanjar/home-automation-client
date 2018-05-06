package client.refresh;

import client.controller.HomeController;
import client.model.Item;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class RefreshItemService extends Service<List<Item>> {

    @Override
    protected Task<List<Item>> createTask() {
        return new Task<List<Item>>() {
            @Override
            protected List<Item> call() throws Exception {
                return HomeController.datasource.itemsQuery();
            }
        };
    }
}
