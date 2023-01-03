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

public class ProlongRentalPage extends MenuPageBase {

    private static final String PAGE_HEADER = "Prolong rental";

    private final IRentalEntryService _rentalService;
    private final IMemberRepository _memberRepository;

    private UI.UIElements.Menu _chooseRentalItemMenu;

    public ProlongRentalPage(Application app) {
        super(PAGE_HEADER, app);
        this._rentalService = app.getCoreServices().getIRentalEntryService();
        this._memberRepository = app.getInfraServices().getIMemberRepository();
    }

    @Override
    public void display() {
        initializeUserMenu();

        this.getMenu().display();

        if (this._chooseRentalItemMenu != null) {
//            Console.Clear();
            this._chooseRentalItemMenu.display();
        }

        InputHelper.readKey("Press any key to return to rentals menu...");
        this.getApplication().navigateBack();
    }


    private void initializeUserMenu() {
        var members = _memberRepository.getAll();
        this.setMenu(new Menu());

        for (int i = 0; i < members.size(); i++) {
            var member = members.get(i);
            this.getMenu().add(i + 1, String.format("%1$s", member.toString()), () -> initializeItemsMenu(member));
        }
    }

    private void initializeItemsMenu(Member member) {
        var items = _rentalService.getByUnreturnedMember(member.getId());

        if (items.size() == 0) {
            OutputHelper.writeLine("No rentals to prolong...");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            var item = items.get(i);
            _chooseRentalItemMenu = new Menu();
            _chooseRentalItemMenu.add(i + 1, String.format("%1$s", item.toString()), () -> prolongRental(item));
        }
    }

    private void prolongRental(RentalEntry entry) {
        if (!_rentalService.canProlongRental(entry)) {
            return;
        }
        _rentalService.prolongRental(entry);

        OutputHelper.writeLine("Rental prolonged successfully...");
    }
}
