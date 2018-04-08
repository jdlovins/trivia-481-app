package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 4/7/18.
 */

public class UpdateLogEvent extends BaseEvent {
    public String message;

    public UpdateLogEvent(String message) {
        this.type = EventType.UPDATE_LOG;
        this.message = message;
    }
}
