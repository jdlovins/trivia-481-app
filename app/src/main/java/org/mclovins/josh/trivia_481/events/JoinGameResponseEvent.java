package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/27/18.
 */

public class JoinGameResponseEvent extends BaseEvent {

    public boolean success;
    public String message;
    public int code;

    public JoinGameResponseEvent(boolean success, String message, int code) {
        this.type = EventType.JOIN_GAME_RESPONSE;
        this.success = success;
        this.message = message;
        this.code = code;
    }

}
