package org.mclovins.josh.trivia_481.events.lists;

import org.mclovins.josh.trivia_481.events.BaseEvent;
import org.mclovins.josh.trivia_481.events.EventType;

/**
 * Created by Josh on 4/7/18.
 */

public class RoundOverEvent extends BaseEvent {

    public RoundOverEvent() {
        this.type = EventType.ROUND_OVER;
    }
}
