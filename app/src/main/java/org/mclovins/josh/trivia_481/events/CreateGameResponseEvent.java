package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 3/6/18.
 */

public class CreateGameResponseEvent extends BaseEvent {

    public boolean success;
    public String message;

    public CreateGameResponseEvent(boolean success, String message) {
        this.type = EventType.JOIN_GAME_RESPONSE;
        this.success = success;
        this.message = message;
    }
}
