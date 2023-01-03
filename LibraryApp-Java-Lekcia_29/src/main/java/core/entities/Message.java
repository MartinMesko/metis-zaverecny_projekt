package core.entities;

import core.base.EntityBase;

public class Message extends EntityBase {

    private Member Member = null;
    private int MemberId;
    private String MessageContext;
    private String MessageSubject;
    private String SendData;

    public final Member getMember() {
        return Member;
    }

    public final void setMember(Member value) {
        Member = value;
    }

    public final int getMemberId() {
        return MemberId;
    }

    public final void setMemberId(int value) {
        MemberId = value;
    }

    public final String getMessageContext() {
        return MessageContext;
    }

    public final void setMessageContext(String value) {
        MessageContext = value;
    }

    public final String getMessageSubject() {
        return MessageSubject;
    }

    public final void setMessageSubject(String value) {
        MessageSubject = value;
    }

    public final String getSendData() {
        return SendData;
    }

    public final void setSendData(String value) {
        SendData = value;
    }

}
