package org.mclovins.josh.trivia_481;

import org.mclovins.josh.trivia_481.events.BaseEvent;

/**
 * Created by Josh on 2/7/18.
 */

public class Packet {
    private BaseEvent event;

    public Packet(BaseEvent event) {
        this.event = event;
    }
}
