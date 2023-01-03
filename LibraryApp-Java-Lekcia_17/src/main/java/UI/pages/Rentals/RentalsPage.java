package UI.pages.Rentals;

import UI.Application;
import UI.base.MenuPageBase;

public class RentalsPage extends MenuPageBase {
    private static final String PAGE_HEADER = "Rentals page";

    public RentalsPage(Application app) {
        super(PAGE_HEADER, app);
        initializeMenu();
    }

    @Override
    public void display() {
        super.display();

        this.getMenu().display();
    }

    private void initializeMenu() {
        this.getMenu().add(1, "Back", () -> this.getApplication().navigateBack());
    }
}

