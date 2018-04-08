package org.mclovins.josh.trivia_481;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.mclovins.josh.trivia_481.events.*;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.mclovins.josh.trivia_481.events.lists.AnswerInfo;
import org.mclovins.josh.trivia_481.events.lists.PlayerInfo;
import org.mclovins.josh.trivia_481.events.lists.RoundOverEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_layout)
    ConstraintLayout main_layout;

    @BindView(R.id.pbMain)
    RoundCornerProgressBar pbMain;

    @BindView(R.id.tvStatus)
    TextView tvStatus;

    @BindView(R.id.tvQuestion)
    TextView tvQuestions;

    @BindView(R.id.tvLog)
    TextView tvLog;

    @BindView(R.id.btnA)
    Button btnA;

    @BindView(R.id.btnB)
    Button btnB;

    @BindView(R.id.btnC)
    Button btnC;

    @BindView(R.id.btnD)
    Button btnD;

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

    int currentQuestionPK = 0;
    List<AnswerInfo> answerList = new ArrayList<>();

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

        tvStatus.setText("Waiting for players...");

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

        btnA.setOnClickListener(onButtonClick);
        btnB.setOnClickListener(onButtonClick);
        btnC.setOnClickListener(onButtonClick);
        btnD.setOnClickListener(onButtonClick);

        tvLog.setText("");
        tvQuestions.setText("");

        toggleButtons(false);

        WebSocketClient.Send(new GameInfoRequestEvent(code).toJson());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameInfoResponse(GameInfoResponseEvent event) {
        Log.d("text", "onGameInfoResponse called");
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
                playerItems.add(new MenuPlayerItem(player.name, identifier, player.points, player.creator));
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
        playerItems.add(new MenuPlayerItem(event.player.name, identifier, 0, event.player.creator));
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
                        menu.updateName(--subItem.id, new StringHolder(subItem.name + " - [" + subItem.points + " points]"));
                    }
                }
                toRemove = item;
            }
        }
        if (toRemove != null)
            playerItems.remove(toRemove);

        UpdatePlayerCount(--currentPlayers);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdatePlayerList(UpdatePlayerListEvent event) {
        for (PlayerInfo pi : event.players) {
            Log.d("text", "Got player update " + pi.name + " - " + pi.points);


            for (MenuPlayerItem mpi : playerItems) {
                if (pi.name.equals(mpi.name)) {
                    mpi.points = pi.points;
                }
            }
        }


        int num = playerItems.size();
        for (int i = 0; i < num; i++) {
            for (int j = 1; j < num-i; j++) {
                MenuPlayerItem c1 = playerItems.get(j-1);
                MenuPlayerItem c2 = playerItems.get(j);

                if ((c1.points < c2.points) && (c1.id < c2.id)) {
                    long temp = c1.id;
                    c1.id = c2.id;
                    c2.id = temp;
                    Collections.swap(playerItems, j -1, j);
                }
            }
        }

        for(MenuPlayerItem pi : playerItems) {
            Drawable icon = ResourcesCompat.getDrawable(getResources(), (pi.creator ? R.drawable.ic_crown_white_24dp : R.drawable.ic_account_circle_white_24dp), null);
            menu.updateName(pi.id, new StringHolder(pi.name + " - [" + pi.points + " points ]"));
            menu.updateIcon(pi.id, new ImageHolder(icon));
        }


    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameStarted(GameStartedEvent event) {
        
        pbMain.setMax(15);
        pbMain.setProgress(15);

        tvStatus.setText("Game Started!");
        toggleButtons(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameCountdown(GameCountdownEvent event) {
        Log.d("Text", "Setting the progress bar max to 30");
        pbMain.setMax(10);
        pbMain.setProgress(10);

        tvStatus.setText("Game starting soon...");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateProgress(UpdateProgressEvent event) {
        pbMain.setProgress(event.progress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQuestInfo(QuestionInfoEvent event) {

        Collections.sort(event.answers, new AnswerButtonCompare());

        currentQuestionPK = event.pk;
        answerList = event.answers;

        String text = event.question + " ( " + event.category + " ) \n\n";

        for(AnswerInfo i : event.answers) {
            text += i.button + ": " + i.answer + "\n";
        }

        tvQuestions.setText(text);
        toggleButtons(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCouldNotConnect(CouldNotConnectEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateProgressMax(UpdateProgressMaxEvent event) {
        pbMain.setMax(event.max);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateStatusMessage(UpdateStatusMessageEvent event) {
        tvStatus.setText(event.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoundOver(RoundOverEvent event) {
        toggleButtons(false);
        tvQuestions.setText("");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLog(UpdateLogEvent event) {
        tvLog.append(event.message);
    }

    View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pk = -1;
            switch (view.getId()) {
                case R.id.btnA:
                    pk = findAnswerPKByButton("A");
                    break;
                case R.id.btnB:
                    pk = findAnswerPKByButton("B");
                    break;
                case R.id.btnC:
                    pk = findAnswerPKByButton("C");
                    break;
                case R.id.btnD:
                    pk = findAnswerPKByButton("D");
                    break;
            }

            toggleButtons(false);
            Toast.makeText(getApplicationContext(), "We should send pk: " + pk, Toast.LENGTH_SHORT).show();

            WebSocketClient.Send(new SendAnswerEvent(currentQuestionPK, pk).toJson());
        }
    };

    private int findAnswerPKByButton(String button) {
        for(AnswerInfo i : answerList) {
            if (i.button.equals(button)) {
                return i.pk;
            }
        }
        return -1;
    }

    private void toggleButtons(boolean enabled) {

        btnA.setEnabled(enabled);
        btnB.setEnabled(enabled);
        btnC.setEnabled(enabled);
        btnD.setEnabled(enabled);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (!shouldWeCloseNow) {
                shouldWeCloseNow = true;
                Toast.makeText(this, "Press back 1 more time to exit", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Log.d("text", "Closing the activity");
                WebSocketClient.Disconnect();
                EventBus.getDefault().unregister(this);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class AnswerButtonCompare implements Comparator<AnswerInfo> {
        @Override
        public int compare(AnswerInfo o1, AnswerInfo o2) {

            return o1.button.compareTo(o2.button);
        }
    }

    private void UpdatePlayerCount(int newPlayerCount) {
        players_header.withName(String.format(Locale.US,"Players [%d/%d]", newPlayerCount, maxPlayers));
        menu.updateItem(players_header);
    }
}