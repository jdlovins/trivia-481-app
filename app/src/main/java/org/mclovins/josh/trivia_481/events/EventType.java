package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/6/18.
 */

public enum EventType {
    BASE("BASE"),
    CREATE_GAME("CREATE_GAME"),
    CREATE_GAME_RESPONSE("CREATE_GAME_RESPONSE"),
    LOGIN("LOGIN"),
    BROADCAST("BROADCAST"),
    JOIN_GAME("JOIN_GAME"),
    JOIN_GAME_RESPONSE("JOIN_GAME_RESPONSE"),
    GAME_INFO_REQUEST("GAME_INFO_REQUEST"),
    GAME_INFO_RESPONSE("GAME_INFO_RESPONSE"),
    USER_JOIN("USER_JOIN"),
    USER_LEFT("USER_LEFT")
    ;

    private final String text;

    /**
     * @param text
     */
    private EventType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
