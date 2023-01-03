package core.events.eventArgs;

import core.entities.Title;

public class TitleReturnedEventArgs {

   private final Title title;

    public TitleReturnedEventArgs(Title title) {
        this.title = title;
    }

    public Title getTitle() {
        return title;
    }

}
