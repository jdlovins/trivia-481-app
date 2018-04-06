package org.mclovins.josh.trivia_481.events;

import org.mclovins.josh.trivia_481.events.lists.PlayerInfo;

import java.util.List;

/**
 * Created by Josh on 4/6/18.
 */

public class UpdatePlayerListEvent extends BaseEvent {

    public List<PlayerInfo> players;

    public UpdatePlayerListEvent(List<PlayerInfo> players) {
        this.type = EventType.UPDATE_PLAYER_LIST;
        this.players = players;
    }
}
