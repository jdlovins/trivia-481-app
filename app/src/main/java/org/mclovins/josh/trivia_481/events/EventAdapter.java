package org.mclovins.josh.trivia_481.events;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.mclovins.josh.trivia_481.events.lists.RoundOverEvent;

import java.lang.reflect.Type;

/**
 * Created by Josh on 2/6/18.
 */

public class EventAdapter implements JsonDeserializer<BaseEvent> {
    @Override
    public BaseEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        EventType type = EventType.valueOf(jsonObject.get("type").getAsString());

        try {
            if (EventType.CREATE_GAME == type) {
                return context.deserialize(jsonObject, CreateGameEvent.class);
            } else if (EventType.CREATE_GAME_RESPONSE == type) {
                return context.deserialize(jsonObject, CreateGameResponseEvent.class);
            } else if (EventType.BROADCAST == type) {
                return context.deserialize(jsonObject, BroadcastEvent.class);
            } else if (EventType.JOIN_GAME_RESPONSE == type) {
                return context.deserialize(jsonObject, JoinGameResponseEvent.class);
            } else if (EventType.GAME_INFO_RESPONSE == type) {
                return context.deserialize(jsonObject, GameInfoResponseEvent.class);
            } else if (EventType.USER_JOIN == type) {
                return context.deserialize(jsonObject, UserJoinEvent.class);
            } else if (EventType.USER_LEFT == type) {
                return context.deserialize(jsonObject, UserLeftEvent.class);
            } else if (EventType.UPDATE_PROGRESS == type){
                return context.deserialize(jsonObject, UpdateProgressEvent.class);
            } else if (EventType.GAME_COUNTDOWN_STARTED == type) {
                return context.deserialize(jsonObject, GameCountdownEvent.class);
            } else if (EventType.GAME_STARTED == type) {
                return context.deserialize(jsonObject, GameStartedEvent.class);
            } else if (EventType.QUESTION_INFO == type) {
                return context.deserialize(jsonObject, QuestionInfoEvent.class);
            } else if (EventType.UPDATE_PLAYER_LIST == type) {
                return context.deserialize(jsonObject, UpdatePlayerListEvent.class);
            } else if (EventType.UPDATE_STATUS_MESSAGE == type) {
                return context.deserialize(jsonObject, UpdateStatusMessageEvent.class);
            } else if (EventType.UPDATE_PROGRESS_MAX == type){
                return context.deserialize(jsonObject, UpdateProgressMaxEvent.class);
            } else if (EventType.ROUND_OVER == type) {
                return context.deserialize(jsonObject, RoundOverEvent.class);
            } else if (EventType.UPDATE_LOG == type) {
                return context.deserialize(jsonObject, UpdateLogEvent.class);
            }

        } catch(Exception e) {
        }

        return null;
    }
}
