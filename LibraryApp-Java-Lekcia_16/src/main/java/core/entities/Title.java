package core.entities;

import core.base.EntityBase;

public abstract class Title extends EntityBase {

    private String Author;
    private String Name;
    private int AvailableCopies;

    public Title(String author, String name, int availableCopies) {
        Author = author;
        Name = name;
        AvailableCopies = availableCopies;
    }

    public Title() {

    }

    public final String getAuthor() {
        return Author;
    }

    public final void setAuthor(String value) {
        Author = value;
    }

    public final String getName() {
        return Name;
    }

    public final void setName(String value) {
        Name = value;
    }

    public final int getAvailableCopies() {
        return AvailableCopies;
    }

    public final void setAvailableCopies(int value) {
        AvailableCopies = value;
    }
}

