package org.mclovins.josh.trivia_481;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.mclovins.josh.trivia_481.events.BaseEvent;
import org.mclovins.josh.trivia_481.events.BroadcastEvent;
import org.mclovins.josh.trivia_481.events.CouldNotConnectEvent;
import org.mclovins.josh.trivia_481.events.EventAdapter;
import org.mclovins.josh.trivia_481.events.EventType;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Josh on 2/1/18.
 */

public class WebSocketClient {

    // Not really happy with this code, probably definitely a better way to do this.

    private static OkHttpClient client = new OkHttpClient();
    private static WebSocket ws;
    public static boolean Connected = false;

    static void Connect() {

        if (Connected)
            return;

        Request request = new Request.Builder().url("ws://139.84.74.131:5000").build();
        ws = client.newWebSocket(request, new WebSocketListenerInterface());
        //client.dispatcher().executorService().shutdown();
    }

    static void Send(String message) {
        if (ws != null)
            ws.send(message);
    }

    static void Disconnect() {
        if (Connected) {
            ws.close(1000, "Goodbye !");
            ws = null;
        }
    }


    private static class WebSocketListenerInterface extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
            Connected = true;
            Log.d("Text", "Connected to web socket server");
        }

        @Override
        public void onMessage(WebSocket webSocket, final String text) {

            Log.d("Text", text);

            Gson gson = new GsonBuilder().registerTypeAdapter(BaseEvent.class, new EventAdapter()).create();
            BaseEvent event = gson.fromJson(text, BaseEvent.class);
            if (event == null) {
                Log.d("text", "COULD NOT PARSE OUT THE EVENT!");
                return;
            }
            EventBus.getDefault().post(event);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Connected = false;
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Connected = false;
            EventBus.getDefault().post(new CouldNotConnectEvent());
            Log.d("Text", "Disconnected from web socket server");
            Log.d("text", t.getStackTrace().toString());

            StackTraceElement elements[] = t.getStackTrace();
            for (int i = 1; i < elements.length; i++) {
                StackTraceElement s = elements[i];
                 Log.d("text", "\tat " + s.getClassName() + "." + s.getMethodName()
                        + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
            }
        }
    }
}


