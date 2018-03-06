package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/15/18.
 */

public class BroadcastEvent extends BaseEvent {

    public String content;

    public BroadcastEvent(String content) {
        this.type = EventType.BROADCAST;
        this.content = content;
    }
}
