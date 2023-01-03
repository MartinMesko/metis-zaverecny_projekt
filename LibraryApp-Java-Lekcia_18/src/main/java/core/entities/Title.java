package core.entities;

import core.base.EntityBase;

public abstract class Title extends EntityBase {

    private String author;
    private String name;
    private int availableCopies;

    public Title(String author, String name, int availableCopies) {
        this.author = author;
        this.name = name;
        this.availableCopies = availableCopies;
    }

    public Title() {

    }

    public final String getAuthor() {
        return author;
    }

    public final void setAuthor(String value) {
        author = value;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String value) {
        name = value;
    }

    public final int getAvailableCopies() {
        return availableCopies;
    }

    public final void setAvailableCopies(int value) {
        availableCopies = value;
    }
}

