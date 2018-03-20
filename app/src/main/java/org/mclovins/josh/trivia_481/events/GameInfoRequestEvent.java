package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 3/10/2018.
 */

public class GameInfoRequestEvent extends BaseEvent {

    private int code = -1;

    public GameInfoRequestEvent(int code) {
        this.type = EventType.GAME_INFO_REQUEST;
        this.code = code;
    }
}
