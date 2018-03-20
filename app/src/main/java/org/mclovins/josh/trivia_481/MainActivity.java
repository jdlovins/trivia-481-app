package org.mclovins.josh.trivia_481;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.mclovins.josh.trivia_481.dialogs.CreateGame;
import org.mclovins.josh.trivia_481.dialogs.JoinGame;
import org.mclovins.josh.trivia_481.events.CreateGameEvent;
import org.mclovins.josh.trivia_481.events.CreateGameResponseEvent;
import org.mclovins.josh.trivia_481.events.JoinGameEvent;
import org.mclovins.josh.trivia_481.events.JoinGameResponseEvent;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.menu_options)
    FloatingActionMenu fabMenu;

    @BindView(R.id.main_layout)
    ConstraintLayout content;

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

        fabMenu.close(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateGameResponseEvent(CreateGameResponseEvent event) {

        if (event.success) {
            Intent myIntent = new Intent(getApplicationContext(), GameActivity.class);
            myIntent.putExtra("CODE", event.code);
            myIntent.putExtra("CREATOR", true);
            getApplicationContext().startActivity(myIntent);
        }
        else {
            showError(event.message);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnJoinGameEvent(JoinGameEvent event) {

        Log.e("test", "We are joining the game room!");

        WebSocketClient.Connect();
        WebSocketClient.Send(event.toJson());

        fabMenu.close(true);
    }

    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void OnJoinGameResponseEvent(JoinGameResponseEvent event) {

        if (event.success) {
            Intent myIntent = new Intent(getApplicationContext(), GameActivity.class);
            myIntent.putExtra("CODE", event.code);
            myIntent.putExtra("CREATOR", true);
            getApplicationContext().startActivity(myIntent);
        }
        else {
            showError(event.message);
        }
    }

    private void showError(String message) {

        final Snackbar error = Snackbar.make(content, message, Snackbar.LENGTH_INDEFINITE);
        final float fabY = fabMenu.getY();

        error.setAction("Got it!", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.dismiss();
                fabMenu.animate().y(fabY);
            }
        });

        error.show();
        fabMenu.animate().y(fabY - 100);
    }
}
