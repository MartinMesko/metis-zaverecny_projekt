package core.entities;

import core.base.EntityBase;
import core.enums.eTitleType;

public class RentalEntry extends EntityBase {
    private Member member;
    private int memberId;
    private String rentedDate;
    private String returnDate = null;
    private Title title;
    private int titleId;
    private int timesProlonged;
    private eTitleType titleType;

    public final Member getMember() {
        return member;
    }

    public final void setMember(Member value) {
        member = value;
    }

    public final int getMemberId() {
        return memberId;
    }

    public final void setMemberId(int value) {
        memberId = value;
    }

    public final String getRentedDate() {
        return rentedDate;
    }

    public final void setRentedDate(String value) {
        rentedDate = value;
    }

    public final String getReturnDate() {
        return returnDate;
    }

    public final void setReturnDate(String value) {
        returnDate = value;
    }

    public final Title getTitle() {
        return title;
    }

    public final void setTitle(Title value) {
        title = value;
    }

    public final int getTitleId() {
        return titleId;
    }

    public final void setTitleId(int value) {
        titleId = value;
    }

    public final int getTimesProlonged() {
        return timesProlonged;
    }

    public final void setTimesProlonged(int value) {
        timesProlonged = value;
    }

    public final boolean isReturned() {
        return this.getReturnDate() != null;
    }

    public final eTitleType getTitleType() {
        return titleType;
    }

    public final void setTitleType(eTitleType value) {
        titleType = value;
    }

    @Override
    public String toString() {
        return String.format("%1$s - %2$s - Rented on: %3$s - Rented by: %4$s %5$s - Returned: %6$s - Times prolonged: %7$s",
                getTitle().getName(), getTitle().getAuthor(), getRentedDate(), getMember().getFirstName(), getMember().getLastName(), (!isReturned() ? "NOT RETURNED" : getReturnDate()), getTimesProlonged());
    }

}
