package UI.pages.Messages;

import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IMemberRepository;
import core.abstractions.services.IMessagingService;
import core.entities.Member;

import java.util.*;

public class MessagesPage extends MenuPageBase {
    private static final String PAGE_HEADER = "Messages page";

    private final IMessagingService messagingService;
    private final IMemberRepository _members;

    public MessagesPage(UI.Application app) {
        super(PAGE_HEADER, app);
        this.messagingService = app.getCoreServices().getIMessagingService();
        this._members = app.getInfraServices().getIMemberRepository();

    }

    private void initializeMenu() {
        var members = getAllMembers();

        for (int i = 0; i < members.size(); i++) {
            var member = members.get(i);
            getMenu().add(i + 1, String.format("%1$s %2$s", member.getFirstName(), member.getLastName()), () -> showMessagesForMember(member));
        }
    }

    private void showMessagesForMember(Member member) {

//        Console.Clear();
        var messages = this.messagingService.getMessagesForUser(member.getId());

        if (messages.size() == 0) {
            OutputHelper.writeLine("No messages to display...");
            return;
        }

        var sb = new StringBuilder();
        for (var message : messages) {
            sb.append(String.format("Sent: %1$s - Subject: %2$s %3$s %4$s %5$s %6$s",
                    message.getSendData(), message.getMessageSubject(), System.lineSeparator(), System.lineSeparator(), message.getMessageContext(), System.lineSeparator())).append("\r\n");
        }

        OutputHelper.writeLine(sb.toString());
    }

    private ArrayList<Member> getAllMembers() {
        return _members.getAll();
    }

    @Override
    public void display() {
        super.display();
        this.getMenu().clearOptions();
        this.initializeMenu();

        getMenu().display();
        InputHelper.readKey("Press any key to return to Main menu...");
        this.getApplication().navigateBack();
    }

}
