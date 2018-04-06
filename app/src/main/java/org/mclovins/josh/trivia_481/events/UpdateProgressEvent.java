package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 3/29/18.
 */

public class UpdateProgressEvent extends BaseEvent {

    public int progress;

    public UpdateProgressEvent(int progress) {
        this.type = EventType.UPDATE_PROGRESS;
        this.progress = progress;
    }
}
