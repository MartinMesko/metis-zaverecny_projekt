package UI.pages.Members;


import UI.Application;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IMemberRepository;
import core.entities.Member;

import java.util.ArrayList;

public class AllMembersPage extends MenuPageBase {
    private static final String PAGE_HEADER = "All members page";
    private final IMemberRepository _memberRepository;

    public AllMembersPage(Application app) {
        super(PAGE_HEADER, app);
        this._memberRepository = app.getInfraServices().getIMemberRepository();
    }

    @Override
    public void display() {
        super.display();

        displayAllMembers();
    }

    private void displayAllMembers() {
        var members = getMembers();
        var sb = new StringBuilder();

        for (var member : members) {
            sb.append(member.toString()).append("\r\n");
        }

        OutputHelper.writeLine(sb.toString());

        InputHelper.readKey("Press any key to return to Members page...");
        this.getApplication().navigateBack();
    }

    private ArrayList<Member> getMembers() {
        return this._memberRepository.getAll();
    }

}

