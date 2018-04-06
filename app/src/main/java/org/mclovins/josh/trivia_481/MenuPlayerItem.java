package org.mclovins.josh.trivia_481;

/**
 * Created by Josh on 3/11/2018.
 */

public class MenuPlayerItem {
    public String name;
    public long id;
    public int points;
    public boolean creator;

    public MenuPlayerItem(String name, long id, int points, boolean creator) {
        this.name = name;
        this.id = id;
        this.points = points;
        this.creator = creator;
    }
}
