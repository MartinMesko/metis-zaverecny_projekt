package UI.pages.Members;

import UI.Application;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IMemberRepository;
import core.entities.Member;

public class AddMemberPage extends MenuPageBase {

    private static final String PAGE_HEADER = "Add member page";
    private final IMemberRepository _memberRepository;

    public AddMemberPage(Application app) {
        super(PAGE_HEADER, app);
        this._memberRepository = app.getServices().getIMemberRepository();
    }

    @Override
    public void display() {
        super.display();

        addNewMember();
    }

    private void addNewMember() {
        var newMember = new Member();

        newMember.setFirstName(InputHelper.readString("Enter member's first name: "));
        newMember.setLastName(InputHelper.readString("Enter member's last name: "));
        newMember.setDateOfBirth(InputHelper.readDateTime("Enter member's date of birth (dd.MM.yyyy): "));
        newMember.setPersonalId(InputHelper.readString("Enter member's personal id: "));

        try {
            var result = this._memberRepository.create(newMember);

            if (result == null) {
                OutputHelper.writeLine("Member not created");
            } else {
                OutputHelper.writeLine("Member created successfully");
            }
        } catch (RuntimeException ex) {
            OutputHelper.writeLine("Member not created");
        } finally {
            InputHelper.readKey("Press any key to return to Members page....");
        }

        this.getApplication().navigateBack();
    }

}

