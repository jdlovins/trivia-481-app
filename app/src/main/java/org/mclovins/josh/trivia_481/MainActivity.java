package org.mclovins.josh.trivia_481;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DialogCreateGame.CreateGameListener {

    @BindView(R.id.menu_options)
    FloatingActionMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
        ButterKnife.bind(this);

        Log.e("test", "test");
    }

    @OnClick(R.id.item_create_game)
    public void OnClick() {
        DialogCreateGame dialog = new DialogCreateGame();
        dialog.show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void CreateGame(String Username, int Time, int Rounds, int Players) {

        WebSocketClient.Connect();
        Intent myIntent = new Intent(getApplicationContext(), GameActivity.class);
        getApplicationContext().startActivity(myIntent);

        fabMenu.close(true);
    }
}
