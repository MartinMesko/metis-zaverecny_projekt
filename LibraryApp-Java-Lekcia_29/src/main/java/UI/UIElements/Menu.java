package UI.UIElements;

import UI.helpers.InputHelper;
import UI.helpers.OutputHelper;
import utils.tangible.Action0Param;

import java.util.*;

public class Menu {
    private ArrayList<Option> options;

    public Menu() {
        setOptions(new ArrayList<Option>());
    }

    public final void display() {
        for (var option : getOptions()) {
            OutputHelper.writeLine(String.format("%1$s - %2$s", option.getOrdinal(), option.getName()));
        }

        int choice = InputHelper.readInt("Choose an option: ", 1, getOptions().size());

        getOptions().get(choice - 1).getCallBack().invoke();
    }

    public final Menu add(int ordinal, String option, Action0Param callback) {
        return add(new Option(ordinal, option, callback));
    }

    public final Menu add(Option option) {
        getOptions().add(option);
        return this;
    }

    private ArrayList<Option> getOptions() {
        return options;
    }

    private void setOptions(ArrayList<Option> value) {
        options = value;
    }

    public void clearOptions() {
        this.getOptions().clear();
    }

}
