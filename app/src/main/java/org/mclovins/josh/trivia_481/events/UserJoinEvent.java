package org.mclovins.josh.trivia_481.events;

import org.mclovins.josh.trivia_481.PlayerInfo;

/**
 * Created by Josh on 3/11/2018.
 */

public class UserJoinEvent extends BaseEvent {
    public PlayerInfo player;

    public UserJoinEvent(PlayerInfo player) {
        this.type = EventType.USER_JOIN;
        this.player = player;
    }
}
