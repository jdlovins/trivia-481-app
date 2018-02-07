package org.mclovins.josh.trivia_481;

import org.mclovins.josh.trivia_481.events.BaseEvent;
import org.mclovins.josh.trivia_481.events.EventType;

/**
 * Created by Josh on 2/7/18.
 */

public class Packet {
    public BaseEvent Event;

    public Packet(BaseEvent event) {
        this.Event = event;
    }
}
