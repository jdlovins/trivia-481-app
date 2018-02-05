package org.mclovins.josh.trivia_481;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {

    WebSocketClient wsc;
    @BindView(R.id.textView) TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        WebSocketClient.Send("Test1111");
        WebSocketClient.Disconnect();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TestEvent tevent) {
        test.setText("holy shit!");
    }
}
