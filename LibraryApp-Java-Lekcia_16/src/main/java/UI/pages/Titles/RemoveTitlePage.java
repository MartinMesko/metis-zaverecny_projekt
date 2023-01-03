package UI.pages.Titles;

import UI.Application;
import UI.base.PageBase;
import UI.helpers.InputHelper;

public class RemoveTitlePage extends PageBase {
    private static final String PAGE_HEADER = "Remove Title Page";

    public RemoveTitlePage(Application app) {
        super(PAGE_HEADER, app);
    }

    @Override
    public void display() {
        super.display();

        InputHelper.ReadKey("Press enter to continue...");
        this.getApplication().navigateBack();
    }
}
