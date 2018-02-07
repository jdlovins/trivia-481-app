package org.mclovins.josh.trivia_481;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.mclovins.josh.trivia_481.events.BaseEvent;
import org.mclovins.josh.trivia_481.events.CreateGameEvent;
import org.mclovins.josh.trivia_481.events.EventAdapter;

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
    public void OnClick() {
        DialogCreateGame dialog = new DialogCreateGame();
        dialog.show(getSupportFragmentManager(), "tag");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateGameEvent(CreateGameEvent event) {

        WebSocketClient.Connect();

        WebSocketClient.Send(event.toJson());

        Intent myIntent = new Intent(getApplicationContext(), GameActivity.class);
        getApplicationContext().startActivity(myIntent);

        fabMenu.close(true);

    }
}
