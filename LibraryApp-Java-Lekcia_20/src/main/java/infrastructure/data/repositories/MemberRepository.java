package infrastructure.data.repositories;

import core.abstractions.repositories.IMemberRepository;
import core.entities.Member;
import infrastructure.data.DatabaseContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberRepository implements IMemberRepository {

    public ArrayList<Member> getAll() {
        String selectStmt = "SELECT * FROM librarydb.members";

        try {
            ResultSet rsMembers = DatabaseContext.dbExecuteQuery(selectStmt);
            return getAllMembers(rsMembers);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<Member> getAllMembers(ResultSet rs) throws SQLException {
        ArrayList<Member> members = new ArrayList<>();

        while (rs.next()) {
            Member member = new Member();
            getMemberFromResultSet(rs, member);
            members.add(member);
        }

        return members;
    }

    @Override
    public Member create(Member entity) {
        String insertStmt = "" +
                "INSERT INTO librarydb.members " +
                "(FirstName, LastName, PersonalId, DateOfBirth) " +
                "VALUES (" + "'" + entity.getFirstName() + "'" + ", " + "'" + entity.getLastName() + "'" + ", " + "'" + entity.getPersonalId() + "'" + ", " + "'" + entity.getDateOfBirth() + "'" + ")";

        try {
            DatabaseContext.dbExecuteUpdate(insertStmt);
            return entity;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Member delete(int id) {
        String deleteStmt = "DELETE FROM librarydb.members WHERE id = " + id;

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
    public Member getById(int id) {
        String selectStmt = "SELECT * FROM librarydb.members WHERE Id = " + id;

        try {
            ResultSet resultSet = DatabaseContext.dbExecuteQuery(selectStmt);
            return getMemberFromResultSet(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Member getMemberFromResultSet(ResultSet rs) throws SQLException {
        Member member = null;
        if (rs.next()) {
            member = new Member();
            getMemberFromResultSet(rs, member);
        }
        return member;
    }

    private void getMemberFromResultSet(ResultSet rs, Member member) throws SQLException {
        member.setId(rs.getInt("Id"));
        member.setFirstName(rs.getString("FirstName"));
        member.setLastName(rs.getString("LastName"));
        member.setPersonalId(rs.getString("PersonalId"));
        member.setDateOfBirth(rs.getString("DateOfBirth"));
    }

}
