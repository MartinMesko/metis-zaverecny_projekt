package UI.pages.Titles;

import UI.Application;
import UI.base.PageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.entities.Book;
import core.entities.Title;

import java.util.ArrayList;

public class RemoveTitlePage extends PageBase {
    private static final String PAGE_HEADER = "Remove Title Page";
    public final IBookRepository _bookRepository;
    private final IDvdRepository _dvdRepository;

    public RemoveTitlePage(Application app) {
        super(PAGE_HEADER, app);
        this._dvdRepository = app.getServices().getIDvdRepository();
        this._bookRepository = app.getServices().getIBookRepository();
    }

    @Override
    public void display() {
        super.display();
        try {
            var titles = this.getTitles();

            if (titles.isEmpty()) {
                noTitleAvailable();
            }

            OutputHelper.writeLine("Available titles: ");
            var titlesCount = displayTitles(titles);
            var input = InputHelper.readInt("Select a title to delete: ", 1, titlesCount);
            var titleToRemove = titles.get(input - 1);

            Title result = titleToRemove instanceof Book ? this._bookRepository.delete(titleToRemove.getId()) : this._dvdRepository.delete(titleToRemove.getId());

            if (result != null) {
                OutputHelper.writeLine("Title removed successfully!");
            }
        } catch (RuntimeException ex) {
            OutputHelper.writeLine("Title not removed");
        } finally {
            InputHelper.readKey("Press key to continue...");
            this.getApplication().navigateBack();
        }
    }

    private void noTitleAvailable() {
        InputHelper.readKey("No titles to remove. Press any key to return to titles...");
        this.getApplication().navigateBack();
    }

    private int displayTitles(ArrayList<Title> titles) {
        var sb = new StringBuilder();

        for (int i = 0; i < titles.size(); i++) {
            sb.append(String.format("%1$s. %2$s", i + 1, titles.get(i).toString())).append("\r\n");
        }

        OutputHelper.writeLine(sb.toString());
        return titles.size();
    }

    private ArrayList<Title> getTitles() {
        var result = new ArrayList<Title>();

        result.addAll(this._bookRepository.getAll());
        result.addAll(this._dvdRepository.getAll());

        return result;
    }
}

