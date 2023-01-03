package UI.pages.Members;

import UI.Application;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;

public class AddMemberPage extends MenuPageBase {

    private static final String PAGE_HEADER = "Add member page";

    public AddMemberPage(Application app) {
        super(PAGE_HEADER, app);

    }


    @Override
    public void display() {
        super.display();
        this.AddNewMember();

    }

    private void AddNewMember() {

        InputHelper.readKey("Press enter to return to Members page....");
        this.getApplication().navigateBack();
    }
}

