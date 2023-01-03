package UI.UIElements;

import utils.tangible.Action0Param;

public class Option {

    private String Name;
    private int Ordinal;

    private Action0Param callBack;

    public Option(int ordinal, String name, Action0Param callback) {
        setOrdinal(ordinal);
        setName(name);
        setCallBack(callback);
    }

    public final int getOrdinal() {
        return Ordinal;
    }

    public final void setOrdinal(int value) {
        Ordinal = value;
    }

    public final String getName() {
        return Name;
    }

    public final void setName(String value) {
        Name = value;
    }

    public Action0Param getCallBack() {
        return callBack;
    }

    private void setCallBack(Action0Param value) {
        callBack = value;
    }

}


