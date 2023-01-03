package UI.pages.Titles;

import UI.Application;
import UI.base.MenuPageBase;

public class AddTitlePage extends MenuPageBase {
    private static final String PAGE_HEADER = "Add Title";

    public AddTitlePage(Application app) {
        super(PAGE_HEADER, app);

        initializeOptions();
    }

    @Override
    public void display() {
        super.display();

        this.getMenu().display();
    }

    private void initializeOptions() {
        this.getMenu().add(1, "Back", () -> this.getApplication().navigateBack());
    }
}

