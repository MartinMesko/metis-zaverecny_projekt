package UI.pages.Members;

import UI.Application;
import UI.UIElements.Option;
import UI.base.MenuPageBase;
import UI.helpers.OutputHelper;

public class RemoveMemberPage extends MenuPageBase
{
    private static final String PAGE_HEADER = "Remove member page";

    public RemoveMemberPage(Application app)
    {
        super(PAGE_HEADER, app);


        initializeOptions();
    }



    @Override
    public void display()
    {
        super.display();

        OutputHelper.writeLine("Choose a member to delete: ");
        this.getMenu().display();
    }



    private void initializeOptions()
    {

        this.getMenu().add(new Option(1, "Back", () -> this.getApplication().navigateBack()));
    }

}

