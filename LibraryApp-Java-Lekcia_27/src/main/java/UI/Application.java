package UI;

import UI.base.PageBase;
import UI.helpers.OutputHelper;
import UI.pages.MainPage;
import UI.pages.Members.AddMemberPage;
import UI.pages.Members.AllMembersPage;
import UI.pages.Members.MembersPage;
import UI.pages.Members.RemoveMemberPage;
import UI.pages.Messages.MessagesPage;
import UI.pages.Rentals.*;
import UI.pages.Titles.AddTitlePage;
import UI.pages.Titles.AllTitlesPage;
import UI.pages.Titles.RemoveTitlePage;
import UI.pages.Titles.TitlesPage;

import core.CoreServiceProvider;
import infrastructure.InfrastructureServiceProvider;
import utils.custom_exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class Application {

    // name of the page
    private String Title;
    private HashMap<Class, PageBase> Pages;
    private Stack<PageBase> history;
    private InfrastructureServiceProvider infraServices;

    private CoreServiceProvider coreServices;

    public Application(String title) {
        this.setTitle(title);
        setPages(new HashMap<>());
        setHistory(new Stack<>());
        buildServices();
        buildPages();
    }

    public final void run() {
        try {
//            Console.Title = Title;
            this.setPage(MainPage.class);

            if (getCurrentPage() != null) {
                getCurrentPage().display();
            }
        } catch (RuntimeException | KeyNotFoundException ex) {
            OutputHelper.writeLine(ex.getMessage());
        }
    }

    public final <T extends PageBase> void setPage(Class<T> pageClazz) throws KeyNotFoundException {

        if (getCurrentPage() != null && getCurrentPage().getClass() == pageClazz) {
            getCurrentPage();
            return;
        }
        // leave the current page

        // select the new page
        PageBase nextPage = null;

        if (!Pages.containsKey(pageClazz)) {
            nextPage = Pages.get(pageClazz);
            throw new KeyNotFoundException(String.format("The given page %1$s was not present in the program", nextPage.getClass().getSimpleName()));
        } else {
            nextPage = Pages.get(pageClazz);
        }

        // enter the new page
        history.push(nextPage);

        getCurrentPage();
    }

    public final void navigateHome() {
        while (getHistory().size() > 1) {
            getHistory().pop();
        }

//        Console.Clear();
        if (getCurrentPage() != null) {
            getCurrentPage().display();
        }
    }

    public final void addPage(PageBase page) {
        Class<? extends PageBase> pageType = page.getClass();

        if (getPages().containsKey(pageType)) {
            getPages().put(pageType, page);
        } else {
            getPages().put(pageType, page);
        }
    }

    public final <T extends PageBase> void navigateTo(Class<T> pageClazz) {
        if (getCurrentPage() != null && getCurrentPage().getClass() == pageClazz) {
            getCurrentPage();
            return;
        }

        PageBase nextPage = null;

        if (!(getPages().containsKey(pageClazz) && (nextPage = getPages().get(pageClazz)).equals(nextPage))) {
            throw new RuntimeException();
        }

        getHistory().push(nextPage);
//        Console.Clear();
        if (getCurrentPage() != null) {
            getCurrentPage().display();
        }

        getCurrentPage();
    }

    public final void navigateBack() {
        getHistory().pop();

//        Console.Clear();
        Objects.requireNonNull(getCurrentPage()).display();
        getCurrentPage();
    }

    private void buildServices() {
        this.infraServices = new InfrastructureServiceProvider();
        this.coreServices = new CoreServiceProvider();
    }

    private void buildPages() {
        this.addPage(new MainPage(this));

        //Titles
        this.addPage(new TitlesPage(this));
        this.addPage(new AllTitlesPage(this));
        this.addPage(new AddTitlePage(this));
        this.addPage(new RemoveTitlePage(this));

        // Members
        this.addPage(new MembersPage(this));
        this.addPage(new AllMembersPage(this));
        this.addPage(new AddMemberPage(this));
        this.addPage(new RemoveMemberPage(this));

        // Rentals
        this.addPage(new RentalsPage(this));
        this.addPage(new RentATitlePage(this));
        this.addPage(new ReturnTitlePage(this));
        this.addPage(new ProlongRentalPage(this));
        this.addPage(new AllRentalsPage(this));
        this.addPage(new PastDueRentalsPage(this));

        // Messages
        this.addPage(new MessagesPage(this));
    }

    protected final String getTitle() {
        return Title;
    }

    protected final void setTitle(String value) {
        Title = value;
    }

    private HashMap<Class, PageBase> getPages() {
        return Pages;
    }

    private void setPages(HashMap<Class, PageBase> value) {
        Pages = value;
    }

    private Stack<PageBase> getHistory() {
        return history;
    }

    private void setHistory(Stack<PageBase> value) {
        history = value;
    }

    public final PageBase getCurrentPage() {
        return !getHistory().empty() ? getHistory().peek() : null;
    }

    public InfrastructureServiceProvider getInfraServices() {
        return infraServices;
    }

    public CoreServiceProvider getCoreServices() {
        return coreServices;
    }

    public final void exit() {
        System.exit(0);
    }

}
