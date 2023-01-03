package infrastructure.data.repositories;

import core.entities.Book;
import core.entities.Dvd;
import core.entities.Member;
import core.entities.Title;
import core.enums.eTitleType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapper {

    public Member getMemberFromResultSet(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("MemberId"));
        member.setFirstName(rs.getString("FirstName"));
        member.setLastName(rs.getString("LastName"));
        member.setPersonalId(rs.getString("PersonalId"));
        member.setDateOfBirth(rs.getString("DateOfBirth"));
        return member;
    }

    public Title getTitleFromResultSet(ResultSet rs, eTitleType titleType) throws SQLException {
        Title title = null;
        switch (titleType) {
            case book:
                title = new Book();
                title.setId(rs.getInt("TitleId"));
                title.setAuthor(rs.getString("Author"));
                title.setName(rs.getString("Name"));
                break;
            case dvd:
                title = new Dvd();
                title.setId(rs.getInt("TitleId"));
                title.setAuthor(rs.getString("Author"));
                title.setName(rs.getString("Name"));
                break;
        }
        return title;
    }

}
