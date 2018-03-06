package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/6/18.
 */

public class CreateGameEvent extends BaseEvent {

    public String userName;
    public String roomName;
    public int time;
    public int rounds;
    public int players;

    public CreateGameEvent(String username, String roomName, int time, int rounds, int players) {
        this.type = EventType.CREATE_GAME;
        this.userName = username;
        this.roomName = roomName;
        this.time = time;
        this.rounds = rounds;
        this.players = players;
    }
}
