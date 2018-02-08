package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 2/6/18.
 */

public enum EventType {
    BASE("BASE"),
    CREATE_ROOM("CREATE_ROOM"),
    LOGIN("LOGIN")
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
