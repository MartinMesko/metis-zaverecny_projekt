package UI.pages.Members;

import UI.Application;
import UI.base.MenuPageBase;

public class MembersPage extends MenuPageBase {
    private static final String PAGE_HEADER = "Members page";

    public MembersPage(Application app) {
        super(PAGE_HEADER, app);
        initializeOptions();
    }

    private void initializeOptions() {
        this.getMenu().add(1, "Show all members", () -> this.getApplication().navigateTo(AllMembersPage.class));
        this.getMenu().add(2, "Add new member", () -> this.getApplication().navigateTo(AddMemberPage.class));
        this.getMenu().add(3, "Remove member", () -> this.getApplication().navigateTo(RemoveMemberPage.class));
        this.getMenu().add(4, "Back", () -> this.getApplication().navigateBack());
    }

    @Override
    public void display() {
        super.display();

        this.getMenu().display();
    }
}

