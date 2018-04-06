package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 4/3/18.
 */

public class GameStartedEvent extends BaseEvent {
    public GameStartedEvent() {
        this.type = EventType.GAME_STARTED;
    }
}
