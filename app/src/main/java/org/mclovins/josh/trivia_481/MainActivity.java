package org.mclovins.josh.trivia_481;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.mclovins.josh.trivia_481.dialogs.CreateGame;
import org.mclovins.josh.trivia_481.dialogs.JoinGame;
import org.mclovins.josh.trivia_481.events.CreateGameEvent;
import org.mclovins.josh.trivia_481.events.JoinGameEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.menu_options)
    FloatingActionMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }

    @OnClick(R.id.item_create_game)
    public void OnCreateGameClick() {
        CreateGame dialog = new CreateGame();
        dialog.show(getSupportFragmentManager(), "tag");
    }

    @OnClick(R.id.item_join_game)
    public void OnJoinGameClick() {
        JoinGame dialog = new JoinGame();
        dialog.show(getSupportFragmentManager(), "tag");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateGameEvent(CreateGameEvent event) {

        Log.e("Test", "We are making the game room!");

        WebSocketClient.Connect();

        WebSocketClient.Send(event.toJson());

        Intent myIntent = new Intent(getApplicationContext(), GameActivity.class);
        getApplicationContext().startActivity(myIntent);

        fabMenu.close(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnJoinGameEvent(JoinGameEvent event) {
        Log.e("test", "We are joining the game room!");

        WebSocketClient.Connect();
        WebSocketClient.Send(event.toJson());

        Intent myIntent = new Intent(getApplicationContext(), GameActivity.class);
        getApplicationContext().startActivity(myIntent);

        fabMenu.close(true);
    }
}
