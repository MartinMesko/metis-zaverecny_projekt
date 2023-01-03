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
            member.setId(rs.getInt("Id"));
            member.setFirstName(rs.getString("FirstName"));
            member.setLastName(rs.getString("LastName"));
            member.setPersonalId(rs.getString("PersonalId"));
            member.setDateOfBirth(rs.getDate("DateOfBirth"));
            members.add(member);
        }

        return members;
    }

    @Override
    public Member create(Member entity) {
        return null;
    }

    @Override
    public Member delete(int id) {
        return null;
    }

    @Override
    public Member getById(int id) {
        return null;
    }

}
