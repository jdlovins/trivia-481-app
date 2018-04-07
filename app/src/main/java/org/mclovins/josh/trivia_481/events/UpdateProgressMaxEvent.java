package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 4/7/18.
 */

public class UpdateProgressMaxEvent extends BaseEvent {
    public int max;

    public UpdateProgressMaxEvent(int max) {
        this.type = EventType.UPDATE_PROGRESS_MAX;
        this.max = max;
    }
}
