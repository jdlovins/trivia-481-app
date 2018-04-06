package org.mclovins.josh.trivia_481.events;

import org.mclovins.josh.trivia_481.events.lists.PlayerInfo;

/**
 * Created by Josh on 3/11/2018.
 */

public class UserLeftEvent extends BaseEvent {

    public PlayerInfo player;

    public UserLeftEvent(PlayerInfo player) {
        this.type = EventType.USER_LEFT;
        this.player = player;
    }
}
