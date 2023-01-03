package UI.pages.Titles;

import UI.Application;
import UI.base.MenuPageBase;

public class TitlesPage extends MenuPageBase {

    private static final String TITLES_MENU_HEADER = "Titles";

    public TitlesPage(Application app) {
        super(TITLES_MENU_HEADER, app);
        InitializeOptions();
    }

    private void InitializeOptions() {
        this.getMenu().add(1, "Show All Titles", () -> this.getApplication().navigateTo(AllTitlesPage.class));
        this.getMenu().add(2, "Add Title", () -> this.getApplication().navigateTo(AddTitlePage.class));
        this.getMenu().add(3, "Remove Title", () -> this.getApplication().navigateTo(RemoveTitlePage.class));
        this.getMenu().add(4, "Back", () -> this.getApplication().navigateBack());
    }

    @Override
    public void display() {
        super.display();
        this.getMenu().display();
    }

}

