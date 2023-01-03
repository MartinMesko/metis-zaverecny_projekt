package infrastructure.data.repositories;

import core.abstractions.repositories.IBookRepository;
import core.entities.Book;
import infrastructure.data.DatabaseContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRepository implements IBookRepository {

    @Override
    public ArrayList<Book> getAll() {
        String selectStmt = "SELECT * FROM librarydb.book";

        try {
            ResultSet rsDvds = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllBook(rsDvds);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<Book> getAllBook(ResultSet rs) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        while (rs.next()) {
            Book book = new Book();
            getTitleFromResultSet(rs, book);
            books.add(book);
        }

        return books;
    }

    @Override
    public Book create(Book entity) {
        String insertStmt = "" +
                "INSERT INTO librarydb.book " +
                "(Author, Name, AvailableCopies, NumberOfPages, ISBN) " +
                "VALUES (" + "'" + entity.getAuthor() + "'" + ", " + "'" + entity.getName() + "'" + ", " + entity.getAvailableCopies() + ", " + entity.getNumberOfPages() + ", " + "'" + entity.getISBN() + "'" + ")";
        try {
            DatabaseContext.dbExecuteUpdate(insertStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book delete(int id) {
        String deleteStmt = "DELETE FROM librarydb.book WHERE id = " + id;

        var entity = this.getById(id);
        if (entity == null) {
            return null;
        }

        try {
            DatabaseContext.dbExecuteUpdate(deleteStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book getById(int id) {
        String selectStmt = "SELECT * FROM librarydb.book WHERE Id = " + id;

        try {
            ResultSet rsEmp = DatabaseContext.dbExecuteQuery(selectStmt);
            return getBookFromResultSet(rsEmp);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Book getBookFromResultSet(ResultSet rsBook) throws SQLException {
        Book book = null;
        if (rsBook.next()) {
            book = new Book();
            getTitleFromResultSet(rsBook, book);
        }
        return book;
    }

    private void getTitleFromResultSet(ResultSet rsBook, Book book) throws SQLException {
        book.setId(rsBook.getInt("Id"));
        book.setAuthor(rsBook.getString("Author"));
        book.setName(rsBook.getString("Name"));
        book.setAvailableCopies(rsBook.getInt("AvailableCopies"));
        book.setNumberOfPages(rsBook.getInt("NumberOfPages"));
        book.setISBN(rsBook.getString("ISBN"));
    }

}
