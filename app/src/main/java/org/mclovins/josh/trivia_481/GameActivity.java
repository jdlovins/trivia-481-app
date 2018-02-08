package org.mclovins.josh.trivia_481;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {

    WebSocketClient wsc;
    @BindView(R.id.textView) TextView test;

    private static boolean shouldWeCloseNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);
        //EventBus.getDefault().register(this);

        shouldWeCloseNow = false;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if (!shouldWeCloseNow) {
                shouldWeCloseNow = true;
                Toast.makeText(this, "Press back 1 more time to exit", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                WebSocketClient.Disconnect();
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
