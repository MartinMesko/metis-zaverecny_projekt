package utils.eventHandler;

import core.events.eventArgs.TitleReturnedEventArgs;

public class EventProducer {

    public EventHandler<TitleReturnedEventArgs> myEvent = new EventHandler<>();

    public void onMyEvent(TitleReturnedEventArgs args) {
        myEvent.invoke(this, args);
    }

}
