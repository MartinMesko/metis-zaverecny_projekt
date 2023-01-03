package core.entities;

public class Book extends Title {

    private int NumberOfPages;

    private String ISBN;

    public Book(String author, String name, int availableCopies, int numberOfPages, String ISBN) {
        super(author, name, availableCopies);
        NumberOfPages = numberOfPages;
        this.ISBN = ISBN;
    }

    public Book() {
        super();
    }

    public final int getNumberOfPages() {
        return NumberOfPages;
    }

    public final void setNumberOfPages(int value) {
        NumberOfPages = value;
    }

    public final String getISBN() {
        return ISBN;
    }

    public final void setISBN(String value) {
        ISBN = value;
    }

    @Override
    public String toString() {
        return String.format("Name: %1$s - Author: %2$s | ISBN: %3$s | Number of pages: %4$s | Available copies: %5$s",
                this.getName(), this.getAuthor(), this.getISBN(), this.getNumberOfPages(), this.getAvailableCopies());
    }
}

