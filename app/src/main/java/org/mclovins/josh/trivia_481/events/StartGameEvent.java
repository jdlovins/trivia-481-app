package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 4/11/18.
 */

public class StartGameEvent extends BaseEvent {
    public int code;

    public StartGameEvent(int code) {

        this.type = EventType.START_GAME;
        this.code = code;
    }
}
