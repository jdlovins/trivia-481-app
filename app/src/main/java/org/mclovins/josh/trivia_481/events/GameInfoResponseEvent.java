package org.mclovins.josh.trivia_481.events;

import org.mclovins.josh.trivia_481.events.lists.PlayerInfo;

import java.util.List;

/**
 * Created by Josh on 3/11/2018.
 */

public class GameInfoResponseEvent extends BaseEvent {

    public String title;
    public int maxPlayers;
    public int rounds;
    public int time;
    public List<PlayerInfo> players;
    public boolean success;

    public GameInfoResponseEvent(String title, int max, int rounds, int time, List<PlayerInfo> players, boolean success) {
        this.title = title;
        this.maxPlayers = max;
        this.rounds = rounds;
        this.time = time;
        this.players = players;
        this.success = success;
    }

}