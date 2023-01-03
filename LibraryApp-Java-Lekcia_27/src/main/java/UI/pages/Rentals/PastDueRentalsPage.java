package UI.pages.Rentals;

import UI.Application;
import UI.base.PageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.services.IRentalEntryService;

public class PastDueRentalsPage extends PageBase {
    private static final String PAGE_HEADER = "Past due rentals";

    private final IRentalEntryService _rentalService;

    public PastDueRentalsPage(Application app) {
        super(PAGE_HEADER, app);
        this._rentalService = app.getCoreServices().getIRentalEntryService();
    }

    @Override
    public void display() {
        super.display();

        DisplayPastDueRentals();

        InputHelper.readKey("Press enter to return to Rental page...");
        this.getApplication().navigateBack();
    }

    public final void DisplayPastDueRentals() {
        var entries = _rentalService.getRentalEntriesPastDue();

        for (var entry : entries) {
            OutputHelper.writeLine(entry.toString());
        }

        if (entries.size() == 0) {
            OutputHelper.writeLine("The list of past due rentals is empty.");
        }
    }
}
