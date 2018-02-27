package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/27/18.
 */

public class JoinGameEvent extends BaseEvent {

    public String Username;
    public int Code;

    public JoinGameEvent(String username, int code) {
        this.Type = EventType.JOIN_GAME;
        this.Username = username;
        this.Code = code;
    }
}
