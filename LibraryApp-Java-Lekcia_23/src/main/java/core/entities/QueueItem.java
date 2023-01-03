package core.entities;

import core.base.EntityBase;

import java.time.LocalDateTime;

public class QueueItem extends EntityBase {
    private Member member = null;
    private int memberId;
    private Title title = null;
    private LocalDateTime timeAdded = LocalDateTime.MIN;
    private int titleId;
    private boolean isResolved;

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

    public final LocalDateTime getTimeAdded() {
        return timeAdded;
    }

    public final void setTimeAdded(LocalDateTime value) {
        timeAdded = value;
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

    public final boolean isResolved() {
        return isResolved;
    }

    public final void setResolved(boolean value) {
        isResolved = value;
    }

    @Override
    public String toString() {
        return String.format("MemberId: %1$s Title: %2$s - Member: %3$s %4$s - IsResolved: %5$s",
                getMemberId(), getTitle().getName(), getMember().getFirstName(), getMember().getLastName(), this.isResolved());
    }

}
