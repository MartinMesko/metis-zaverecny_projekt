package UI.pages.Titles;

import UI.Application;
import UI.base.MenuPageBase;
import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.entities.Book;
import core.entities.Dvd;
import core.enums.eTitleType;

import static core.enums.eTitleType.*;

public class AddTitlePage extends MenuPageBase {
    private static final String PAGE_HEADER = "Add Title";
    private final IDvdRepository _dvdRepository;
    private final IBookRepository _bookRepository;

    public AddTitlePage(Application application) {
        super(PAGE_HEADER, application);
        this._dvdRepository = application.getServices().getIDvdRepository();
        this._bookRepository = application.getServices().getIBookRepository();

        initializeOptions();
    }

    @Override
    public void display() {
        super.display();

        this.getMenu().display();
    }

    private void addTitle(eTitleType titleType) {
//        Console.Clear();

        var author = InputHelper.ReadString("Enter Author's name: ");
        var titleName = InputHelper.ReadString("Enter title name: ");
        var numberOfCopies = InputHelper.ReadInt("Enter available copies: ", 1, Integer.MAX_VALUE);

        switch (titleType) {
            case book:
                addBook(author, titleName, numberOfCopies);
                break;
            case dvd:
                addDvd(author, titleName, numberOfCopies);
                break;
            default:
                OutputHelper.WriteLine("Title type not supported");
                break;
        }
    }

    private void addBook(String author, String name, int availableCopies) {
        var book = new Book();

        var isbn = InputHelper.ReadString("Enter ISBN: ");
        var numberOfPages = InputHelper.ReadInt("Enter number of Pages: ", 1, Integer.MAX_VALUE);

        book.setAuthor(author);
        book.setName(name);
        book.setISBN(isbn);
        book.setAvailableCopies(availableCopies);
        book.setNumberOfPages(numberOfPages);

        try {
            var result = _bookRepository == null ? null : _bookRepository.create(book);

            if (result == null) {
                OutputHelper.WriteLine("Book not added!!");
            } else {
                OutputHelper.WriteLine("Book added succesfully.");
            }
        } catch (RuntimeException ex) {
            OutputHelper.WriteLine("Book not added!!");
        } finally {
            InputHelper.ReadKey("Press any key to continue...");
            this.getApplication().navigateBack();

        }
    }

    private void addDvd(String author, String name, int numberOfCopies) {
        var dvd = new Dvd();

        var length = InputHelper.ReadInt("Enter Length (minutes): ", 1, 600);
        var numberOfChapters = InputHelper.ReadInt("Enter number of chapters: ", 1, 25);

        dvd.setAuthor(author);
        dvd.setName(name);
        dvd.setNumberOfMinutes(length);
        dvd.setNumberOfChapters(numberOfChapters);
        dvd.setAvailableCopies(numberOfCopies);

        try {
            var result = _dvdRepository == null ? null : _dvdRepository.create(dvd);

            if (result == null) {
                OutputHelper.WriteLine("Dvd not added!!");
            } else {
                OutputHelper.WriteLine("Dvd added succesfully.");
            }
        } catch (RuntimeException ex) {
            OutputHelper.WriteLine("Dvd not added!!");

        } finally {
            InputHelper.ReadKey("Press any key to continue...");
            this.getApplication().navigateBack();
        }

    }

    private void initializeOptions() {
        this.getMenu().add(1, "Add Book", () -> this.addTitle(book));
        this.getMenu().add(2, "Add Dvd", () -> this.addTitle(dvd));
        this.getMenu().add(3, "Back", () -> this.getApplication().navigateBack());
    }
}
