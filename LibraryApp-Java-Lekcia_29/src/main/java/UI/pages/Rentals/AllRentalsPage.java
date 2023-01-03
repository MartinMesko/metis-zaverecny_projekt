package UI.pages.Rentals;

import UI.Application;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.services.IRentalEntryService;
import core.entities.RentalEntry;

import java.util.ArrayList;

public class AllRentalsPage extends MenuPageBase {
    private static final String PAGE_HEADER = "All rentals";

    private final IRentalEntryService _rentalEntryService;

    public AllRentalsPage(Application app) {
        super(PAGE_HEADER, app);
        this._rentalEntryService = app.getCoreServices().getIRentalEntryService();
    }

    @Override
    public void display() {
        super.display();

        displayAllRentalEntries();

        InputHelper.readKey("Press enter to return to Rentals page...");
        this.getApplication().navigateBack();
    }

    private void displayAllRentalEntries() {
        var entries = getAllRentalEntries();
        var sb = new StringBuilder();

        for (var entry : entries) {
            sb.append(entry.toString()).append("\r\n");
        }

        OutputHelper.writeLine(sb.toString());
    }

    private ArrayList<RentalEntry> getAllRentalEntries() {
        return _rentalEntryService.getAllEntries();
    }
}
