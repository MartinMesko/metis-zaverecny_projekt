package core.entities;

import core.base.EntityBase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Member extends EntityBase {

    public String firstName;
    public String lastName;
    public String personalId;
    public String dateOfBirth;

    public Member(String firstName, String lastName, String personalId, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.dateOfBirth = dateOfBirth;
    }

    public Member() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return String.format("%1$s.| %2$s %3$s | Date of Birth: %4$s - Personal Id: %5$s", getId(), firstName, lastName, dateOfBirth, personalId);
    }

}
