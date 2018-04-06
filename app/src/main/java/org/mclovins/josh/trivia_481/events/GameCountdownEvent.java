package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 4/3/18.
 */

public class GameCountdownEvent extends BaseEvent {

    public GameCountdownEvent() {
        this.type = EventType.GAME_COUNTDOWN_STARTED;
    }
}
