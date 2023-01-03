package UI.pages.Rentals;

import UI.Application;
import UI.UIElements.Menu;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IMemberRepository;
import core.abstractions.services.IRentalEntryService;
import core.entities.Member;
import core.entities.RentalEntry;

import java.util.ArrayList;

public class ReturnTitlePage extends MenuPageBase {

    private static final String PAGE_HEADER = "Return title page";

    private final IRentalEntryService _rentalEntryService;
    private final IMemberRepository _memberRepository;

    private final Menu _chooseTitleMenu = new Menu();
    private final Menu _chooseMemberMenu = new Menu();

    public ReturnTitlePage(Application app) {
        super(PAGE_HEADER, app);

        this._rentalEntryService = app.getCoreServices().getIRentalEntryService();
        this._memberRepository = app.getInfraServices().getIMemberRepository();
    }

    @Override
    public void display() {
        super.display();
        clearReturnTitleMenu();
        initializeMembersMenu();

        OutputHelper.writeLine("Choose a member: ");
        _chooseMemberMenu.display();

        OutputHelper.writeLine("Select a title to return: ");
        _chooseTitleMenu.display();

        InputHelper.readKey("Press enter to return to Rental page...");

        this.getApplication().navigateBack();
    }

    private void initializeMembersMenu() {
        var members = getAllMembers();

        for (int i = 0; i < members.size(); i++) {
            var member = members.get(i);
            this._chooseMemberMenu.add(i + 1, members.get(i).toString(), () -> initializeEntriesMenu(member));
        }
    }

    private void initializeEntriesMenu(Member member) {
        var rentEntries = this._rentalEntryService.getByUnreturnedMember(member.getId());

        for (int i = 0; i < rentEntries.size(); i++) {
            var rentEntry = rentEntries.get(i);
            _chooseTitleMenu.add(i + 1, rentEntries.get(i).toString(), () -> returnTitle(rentEntry));
        }

        if (rentEntries.size() == 0) {
            OutputHelper.writeLine("The list of titles is empty.");
            InputHelper.readKey("Press enter to return to Rental page...");
            this.getApplication().navigateBack();
        }
    }

    private void returnTitle(RentalEntry rentEntry) {
        // isLateCheck
        if (this._rentalEntryService.isEntryPastDue(rentEntry)) {
            OutputHelper.writeLine("You are late with the returning of this title!");

            // feeCalculation
            var fee = this._rentalEntryService.calculateReturnalFee(rentEntry);
            OutputHelper.writeLine("The fee for your late returnal is: " + fee + " Euro");
        }

        this._rentalEntryService.returnEntry(rentEntry);

        OutputHelper.writeLine("Title returned successfully.");
    }

    private ArrayList<Member> getAllMembers() {
        return this._memberRepository.getAll();
    }

    private void clearReturnTitleMenu() {
        this._chooseTitleMenu.clearOptions();
        this._chooseMemberMenu.clearOptions();
    }

}
