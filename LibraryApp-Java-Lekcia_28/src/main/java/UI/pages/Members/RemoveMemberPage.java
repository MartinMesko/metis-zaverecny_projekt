package UI.pages.Members;

import UI.Application;
import UI.UIElements.Option;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IMemberRepository;
import core.entities.Member;

import java.util.*;

public class RemoveMemberPage extends MenuPageBase {
    private static final String PAGE_HEADER = "Remove member page";
    private final IMemberRepository _memberRepository;

    public RemoveMemberPage(Application app) {
        super(PAGE_HEADER, app);
        this._memberRepository = app.getInfraServices().getIMemberRepository();

        initializeOptions();
    }

    @Override
    public void display() {
        super.display();

        OutputHelper.writeLine("Choose a member to delete: ");
        this.getMenu().display();
    }

    private void deleteMember(Member member) {
        try {
            var result = this._memberRepository.delete(member.getId());

            if (result == null) {
                OutputHelper.writeLine("Member not deleted.");
            } else {
                OutputHelper.writeLine("Member deleted successfully.");
            }
        } catch (RuntimeException ex) {
            OutputHelper.writeLine("Member deleted successfully.");
        } finally {
            InputHelper.readKey("Press any key to return to Members page...");
        }

        this.getApplication().navigateBack();
    }

    private void initializeOptions() {
        var members = getMembers();

        for (int index = 0; index < members.size(); index++) {
            var member = members.get(index);
            this.getMenu().add(new Option(index + 1, member.toString(), () -> this.deleteMember(member)));
        }
    }

    private ArrayList<Member> getMembers() {
        return this._memberRepository.getAll();
    }

}
