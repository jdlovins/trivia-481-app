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

        String jsonType = jsonObject.get("Type").getAsString();

        if (EventType.CREATE_ROOM.toString().equals(jsonType)) {
            return context.deserialize(jsonObject, CreateGameEvent.class);
        }

        return null;
    }
}
