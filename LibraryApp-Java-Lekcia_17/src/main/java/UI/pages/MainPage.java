package UI.pages;

import UI.Application;
import UI.base.MenuPageBase;
import UI.helpers.OutputHelper;
import UI.pages.Members.MembersPage;
import UI.pages.Messages.MessagesPage;
import UI.pages.Rentals.RentalsPage;
import UI.pages.Titles.TitlesPage;

public class MainPage extends MenuPageBase {
    private static final String MAIN_PAGE_CONST = ">>>> Welcome to our Library <<<<";

    public MainPage(Application app) {
        super(MAIN_PAGE_CONST, app);
        initializeMenuOptions();
    }

    private void initializeMenuOptions() {
        this.getMenu().add(1, "Titles", () -> this.getApplication().navigateTo(TitlesPage.class));
        this.getMenu().add(2, "Members", () -> this.getApplication().navigateTo(MembersPage.class));
        this.getMenu().add(3, "Rentals", () -> this.getApplication().navigateTo(RentalsPage.class));
        this.getMenu().add(4, "Messages", () -> this.getApplication().navigateTo(MessagesPage.class));
        this.getMenu().add(5, "Exit", () -> this.getApplication().Exit());
    }

    @Override
    public void display() {
//        OutputHelper.WriteLine(">>>> Welcome to our Library <<<<");
        super.display();
        OutputHelper.WriteLine("\n");

        this.getMenu().display();
    }
}
