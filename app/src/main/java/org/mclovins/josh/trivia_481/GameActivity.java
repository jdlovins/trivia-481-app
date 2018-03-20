package org.mclovins.josh.trivia_481;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.mclovins.josh.trivia_481.events.*;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_layout)
    ConstraintLayout main_layout;

    @BindView(R.id.pbMain)
    RoundCornerProgressBar pbMain;

    private static boolean shouldWeCloseNow;
    private Drawer menu;

    private int code;
    private boolean creator;

    private ArrayList<MenuPlayerItem> playerItems = new ArrayList<>();

    PrimaryDrawerItem players_header = new PrimaryDrawerItem().withIdentifier(1).withName("Players").withSelectable(false);
    PrimaryDrawerItem rounds_header = new PrimaryDrawerItem().withIdentifier(2).withName("Rounds").withSelectable(false);
    PrimaryDrawerItem time_header = new PrimaryDrawerItem().withIdentifier(3).withName("Time").withSelectable(false);
    PrimaryDrawerItem code_header = new PrimaryDrawerItem().withIdentifier(4).withName("Code").withSelectable(false);

    int maxPlayers = 0;
    int currentPlayers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        code = getIntent().getIntExtra("CODE", -1);
        creator = getIntent().getBooleanExtra("CREATOR", false);
        shouldWeCloseNow = false;

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        toolbar.setBackground(getResources().getDrawable(R.color.colorPrimary));



        menu = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        rounds_header,
                        time_header,
                        code_header,
                        new DividerDrawerItem(),
                        players_header,
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
                        return false;
                    }
                })
                .build();
        menu.setSelection(-1);

        WebSocketClient.Send(new GameInfoRequestEvent(code).toJson());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameInfoResponse(GameInfoResponseEvent event) {

        if (event.success) {
            toolbar.setTitle(event.title);
            players_header.withName(String.format(Locale.US,"Players [%d/%d]", event.players.size(), event.maxPlayers));
            rounds_header.withName(String.format(Locale.US, "%d rounds", event.rounds));
            time_header.withName(String.format(Locale.US, "%d seconds per round", event.time));
            code_header.withName(String.format(Locale.US, "The game code is %d", code));

            menu.updateItem(players_header);
            menu.updateItem(rounds_header);
            menu.updateItem(time_header);
            menu.updateItem(code_header);

            for (PlayerInfo player : event.players) {
                int identifier = playerItems.size() + 10;
                Drawable icon = ResourcesCompat.getDrawable(getResources(), (player.creator ? R.drawable.ic_crown_white_24dp : R.drawable.ic_account_circle_white_24dp), null);
                menu.addItem(new PrimaryDrawerItem().withIdentifier(identifier).withName(player.name).withSelectable(false).withIcon(icon));
                playerItems.add(new MenuPlayerItem(player.name, identifier));
                currentPlayers++;
            }

            maxPlayers = event.maxPlayers;

        }else {

            final Snackbar error = Snackbar.make(main_layout, "Something went wrong when requesting room information", Snackbar.LENGTH_INDEFINITE);
            error.setAction("Got it!", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error.dismiss();
                }
            });
            error.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserJoin(UserJoinEvent event) {
        int identifier = playerItems.size() + 10;
        Drawable icon = ResourcesCompat.getDrawable(getResources(), (event.player.creator ? R.drawable.ic_crown_white_24dp : R.drawable.ic_account_circle_white_24dp), null);
        menu.addItem(new PrimaryDrawerItem().withIdentifier(identifier).withName(event.player.name).withSelectable(false).withIcon(icon));
        playerItems.add(new MenuPlayerItem(event.player.name, identifier));
        UpdatePlayerCount(++currentPlayers);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserLeft(UserLeftEvent event){

        MenuPlayerItem toRemove = null; // could use iterator here maybe but eh
        for(MenuPlayerItem item : playerItems) {
            if (item.name.equals(event.player.name)) {

                for(int i = 0; i < playerItems.size(); i++) {
                    if (i == playerItems.size() - 1) {
                        menu.removeItem(playerItems.get(i).id);
                    }

                    MenuPlayerItem subItem = playerItems.get(i);
                    if (subItem.id > item.id) {
                        subItem.id--;
                        menu.updateName(subItem.id, new StringHolder(subItem.name));
                    }
                }
                toRemove = item;
            }
        }
        if (toRemove != null)
            playerItems.remove(toRemove);

        UpdatePlayerCount(--currentPlayers);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (!shouldWeCloseNow) {
                shouldWeCloseNow = true;
                Toast.makeText(this, "Press back 1 more time to exit", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                WebSocketClient.Disconnect();
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void UpdatePlayerCount(int newPlayerCount) {
        players_header.withName(String.format(Locale.US,"Players [%d/%d]", newPlayerCount, maxPlayers));
        menu.updateItem(players_header);
    }
}