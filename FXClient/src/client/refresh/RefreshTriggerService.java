package client.refresh;

import client.controllers.HomeController;
import client.model.Trigger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class RefreshTriggerService extends Service<List<Trigger>> {

    @Override
    protected Task<List<Trigger>> createTask() {
        return new Task<List<Trigger>>() {
            @Override
            protected List<Trigger> call() throws Exception {
                return HomeController.datasource.triggersQuery();
            }
        };
    }
}
