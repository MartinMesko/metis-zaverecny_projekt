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
        this.getMenu().add(1, "Rent a title", () -> this.getApplication().navigateTo(RentATitlePage.class));
        this.getMenu().add(2, "Return a title", () -> this.getApplication().navigateTo(ReturnTitlePage.class));
        this.getMenu().add(3, "Prolong the rental", () -> this.getApplication().navigateTo(ProlongRentalPage.class));
        this.getMenu().add(4, "Show all rentals", () -> this.getApplication().navigateTo(AllRentalsPage.class));
        this.getMenu().add(5, "Show rentals past due", () -> this.getApplication().navigateTo(PastDueRentalsPage.class));
        this.getMenu().add(6, "Show Queue", () -> this.getApplication().navigateTo(QueuePage.class));
        this.getMenu().add(7, "Back", () -> this.getApplication().navigateBack());
    }
}

