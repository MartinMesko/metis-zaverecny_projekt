package core.entities;

public class Dvd extends Title {
    private int numberOfChapters;
    private int numberOfMinutes;

    public Dvd(String author, String name, int availableCopies, int numberOfChapters, int numberOfMinutes) {
        super(author, name, availableCopies);
        this.numberOfChapters = numberOfChapters;
        this.numberOfMinutes = numberOfMinutes;
    }

    public Dvd() {
        super();
    }

    public final int getNumberOfChapters() {
        return numberOfChapters;
    }

    public final void setNumberOfChapters(int value) {
        numberOfChapters = value;
    }

    public final int getNumberOfMinutes() {
        return numberOfMinutes;
    }

    public final void setNumberOfMinutes(int value) {
        numberOfMinutes = value;
    }

    @Override
    public String toString() {
        return String.format("Name: %1$s - Author: %2$s - Number of chapters: %3$s - Length in minutes: %4$s - Available copies: %5$s",
                this.getName(), this.getAuthor(), this.getNumberOfChapters(), this.getNumberOfMinutes(), this.getAvailableCopies());
    }

}

