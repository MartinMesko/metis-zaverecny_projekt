package UI.pages.Titles;

import UI.Application;
import UI.base.PageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.entities.Title;

import java.util.ArrayList;
import java.util.List;

public class AllTitlesPage extends PageBase {

    private static final String PAGE_HEADER = "All Titles";

    private final IDvdRepository _dvdRepository;
    private final IBookRepository _bookRepository;

    public AllTitlesPage(Application app) {
        super(PAGE_HEADER, app);
        this._dvdRepository = app.getServices().getIDvdRepository();
        this._bookRepository = app.getServices().getIBookRepository();
    }

    @Override
    public void display() {
        super.display();

        displayAllTitles();

        InputHelper.readKey("Press enter to return to Main menu...");
        this.getApplication().navigateBack();
    }

    private void displayAllTitles() {
        List<Title> titles = getAllTitles();

        if (titles.size() == 0) {
            OutputHelper.writeLine("No titles available");
        }

        StringBuilder sb = new StringBuilder();
        for (var title : titles) {
            sb.append(title.toString()).append("\n");
        }

        OutputHelper.writeLine(sb.toString());
    }

    private List<Title> getAllTitles() {
        List<Title> result = new ArrayList<>();
        result.addAll(this._dvdRepository.getAll());
        result.addAll(this._bookRepository.getAll());

        return result;
    }
}
