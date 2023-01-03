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

    public AllTitlesPage(Application application) {
        super(PAGE_HEADER, application);
        this._dvdRepository = application.getServices().getIDvdRepository();
        this._bookRepository = application.getServices().getIBookRepository();
    }

    @Override
    public void display() {
        super.display();

        displayAllTitles();

        InputHelper.ReadKey("Press enter to return to Main menu...");
        this.getApplication().navigateBack();
    }

    private void displayAllTitles() {
        List<Title> titles = getAllTitles();

        if (titles.size() == 0) {
            OutputHelper.WriteLine("No titles available");
        }

        StringBuilder sb = new StringBuilder();
        for (var title : titles) {
            sb.append(title.toString()).append("\n");
        }

        OutputHelper.WriteLine(sb.toString());
    }

    private List<Title> getAllTitles() {
        List<Title> result = new ArrayList<>();
        result.addAll(this._dvdRepository.getAll());
        result.addAll(this._bookRepository.getAll());

        return result;
    }
}
