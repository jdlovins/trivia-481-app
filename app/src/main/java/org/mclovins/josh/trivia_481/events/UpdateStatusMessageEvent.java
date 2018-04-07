package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 4/7/18.
 */

public class UpdateStatusMessageEvent extends BaseEvent {

    public String message;
    
    public UpdateStatusMessageEvent(String message){
        this.type = EventType.UPDATE_STATUS_MESSAGE;
        this.message = message;
    }
}
