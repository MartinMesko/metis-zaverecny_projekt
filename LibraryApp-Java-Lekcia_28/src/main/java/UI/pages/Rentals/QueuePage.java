package UI.pages.Rentals;

import UI.Application;
import UI.base.PageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.services.IQueueService;
import core.entities.QueueItem;

import java.util.*;

public class QueuePage extends PageBase {
    private static final String PAGE_HEADER = "Queue";

    private final IQueueService _queueItemService;

    public QueuePage(Application app) {
        super(PAGE_HEADER, app);
        this._queueItemService = app.getCoreServices().getIQueueService();
    }

    @Override
    public void display() {
        super.display();

        displayAllQueueItems();

        InputHelper.readKey("Press enter to continue to Rentals Menu...");
        this.getApplication().navigateBack();
    }

    private void displayAllQueueItems() {
        var items = getQueueItems();

        var sb = new StringBuilder();

        for (var item : items) {
            sb.append(item.toString()).append("\r\n");
        }

        OutputHelper.writeLine(sb.toString());
    }

    private ArrayList<QueueItem> getQueueItems() {
        return this._queueItemService.getAllItems();
    }
}
