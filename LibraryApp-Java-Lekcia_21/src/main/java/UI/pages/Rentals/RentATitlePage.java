package UI.pages.Rentals;


import UI.Application;
import UI.UIElements.Menu;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.abstractions.repositories.IMemberRepository;
import core.entities.Member;
import core.entities.Title;

import java.util.ArrayList;

public class RentATitlePage extends MenuPageBase {

    private static final String PAGE_HEADER = "Rent a title";
    private final IMemberRepository _memberRepository;
    private final IDvdRepository _dvdRepository;
    private final IBookRepository _bookRepository;

    private final Menu _chooseTitleMenu = new Menu();
    private final Menu _chooseMemberMenu = new Menu();

    private Title _choosenTitle;
    private Member _choosenMember;

    public RentATitlePage(Application app) {
        super(PAGE_HEADER, app);

        this._memberRepository = app.getServices().getIMemberRepository();
        this._dvdRepository = app.getServices().getIDvdRepository();
        this._bookRepository = app.getServices().getIBookRepository();

        initializeMenu();
    }


    private void initializeMenu() {
        var members = getAllMembers();

        for (int index = 0; index < members.size(); index++) {
            var member = members.get(index);
            _chooseMemberMenu.add(index + 1, member.toString(), () -> this._choosenMember = member);
        }

        var titles = GetAllTitles();

        for (int index = 0; index < titles.size(); index++) {
            var title = titles.get(index);
            _chooseTitleMenu.add(index + 1, title.toString(), () -> this._choosenTitle = title);
        }
    }

    @Override
    public void display() {
        super.display();

        OutputHelper.writeLine("Choose a member to rent to: ");
        this._chooseMemberMenu.display();

//        Console.Clear();

        OutputHelper.writeLine("Choose a title to rent to: ");
        this._chooseTitleMenu.display();

        InputHelper.readKey("Press any key to continue....");
        this.getApplication().navigateBack();
    }


    private ArrayList<Member> getAllMembers() {
        return _memberRepository.getAll();
    }

    private ArrayList<Title> GetAllTitles() {
        var list = new ArrayList<Title>();

        list.addAll(this._dvdRepository.getAll());
        list.addAll(this._bookRepository.getAll());

        return list;
    }

}
