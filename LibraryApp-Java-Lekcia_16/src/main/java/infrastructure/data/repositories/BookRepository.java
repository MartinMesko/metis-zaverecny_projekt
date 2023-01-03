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

        while(rs.next()) {
            Book book = new Book();
            book.setAuthor(rs.getString("Author"));
            book.setName(rs.getString("Name"));
            book.setAvailableCopies(rs.getInt("AvailableCopies"));
            book.setNumberOfPages(rs.getInt("NumberOfPages"));
            book.setISBN(rs.getString("ISBN"));
            books.add(book);
        }

        return books;
    }
}
