package org.mclovins.josh.trivia_481.events;

import org.mclovins.josh.trivia_481.Globals;
import org.mclovins.josh.trivia_481.Packet;

/**
 * Created by Josh on 2/6/18.
 */

public class BaseEvent {

    public EventType Type = EventType.BASE;

    public String toJson() {
        return Globals.gson.toJson(new Packet(this));
    }

}
