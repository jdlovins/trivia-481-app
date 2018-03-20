package org.mclovins.josh.trivia_481.events;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Josh on 2/6/18.
 */

public class EventAdapter implements JsonDeserializer<BaseEvent> {
    @Override
    public BaseEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        EventType type = EventType.valueOf(jsonObject.get("type").getAsString());

        if (EventType.CREATE_GAME == type) {
            return context.deserialize(jsonObject, CreateGameEvent.class);
        }else if (EventType.BROADCAST == type) {
            return context.deserialize(jsonObject, BroadcastEvent.class);
        }else if (EventType.JOIN_GAME_RESPONSE == type) {
            return context.deserialize(jsonObject, JoinGameResponseEvent.class);
        }else if (EventType.GAME_INFO_RESPONSE == type) {
            return context.deserialize(jsonObject, GameInfoResponseEvent.class);
        }else if (EventType.USER_JOIN == type) {
            return context.deserialize(jsonObject, UserJoinEvent.class);
        }else if (EventType.USER_LEFT == type) {
            return context.deserialize(jsonObject, UserLeftEvent.class);
        }

        return null;
    }
}
