package org.mclovins.josh.trivia_481.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.greenrobot.eventbus.EventBus;
import org.mclovins.josh.trivia_481.R;
import org.mclovins.josh.trivia_481.events.CreateGameEvent;

import java.security.Key;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Josh on 1/30/18.
 */

public class CreateGame extends AppCompatDialogFragment {

    @BindView(R.id.input_layout_username)
    TextInputLayout tiUsername;
    @BindView(R.id.input_layout_room_name)
    TextInputLayout tiRoomName;
    @BindView(R.id.input_username)
    EditText etUsername;
    @BindView(R.id.input_room_name)
    EditText etRoomName;
    @BindView(R.id.sbTime)
    DiscreteSeekBar sbTime;
    @BindView(R.id.sbRounds)
    DiscreteSeekBar sbRounds;
    @BindView(R.id.sbPlayers)
    DiscreteSeekBar sbPlayers;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_game, null);

        ButterKnife.bind(this, view);

        // Create the dialog with null listeners since we need to change it later.
        builder.setView(view)
                .setTitle("Game Options")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Create", null);

        // This waits 50ms before popping up the keyboard automatically
        etUsername.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(etUsername, 0);
            }
        }, 50);

        // Listen for enter key to check the form
        etUsername.setOnKeyListener(KeyListener);
        etRoomName.setOnKeyListener(KeyListener);
        etUsername.setOnEditorActionListener(SoftInputListener);
        etRoomName.setOnEditorActionListener(SoftInputListener);

        sbTime.setOnProgressChangeListener(ProgressChangedListener);
        sbRounds.setOnProgressChangeListener(ProgressChangedListener);
        sbPlayers.setOnProgressChangeListener(ProgressChangedListener);

        return builder.create();
    }


    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckForm();
            }
        });
    }

    View.OnKeyListener KeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {

            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                    (i == KeyEvent.KEYCODE_ENTER)) {
                CheckForm();
                return true;
            }
            return false;
        }
    };

    TextView.OnEditorActionListener SoftInputListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            boolean handled = false;
            if (i == EditorInfo.IME_ACTION_DONE) {
                CheckForm();
                handled = true;
            }
            return handled;
        }
    };

    DiscreteSeekBar.OnProgressChangeListener ProgressChangedListener = new DiscreteSeekBar.OnProgressChangeListener() {
        @Override
        public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(seekBar.getWindowToken(), 0);
        }

        @Override
        public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

        }
    };


    private void CheckForm() {

        final AlertDialog alertDialog = (AlertDialog) getDialog();
        boolean error = false;

        if (etUsername.getText().toString().trim().isEmpty()) {
            tiUsername.setError("Please set a username");
            error = true;
        }
        else {
            tiUsername.setErrorEnabled(false);
        }
        if (etRoomName.getText().toString().trim().isEmpty()) {
            tiRoomName.setError("Please set a Room Name");
            error = true;
        }
        else {
            tiRoomName.setErrorEnabled(false);
        }

        if (!error) {
            alertDialog.dismiss();
            EventBus.getDefault().post(new CreateGameEvent(etUsername.getText().toString(),
                    etRoomName.getText().toString(), sbTime.getProgress(),
                    sbRounds.getProgress(), sbPlayers.getProgress()));
        }

    }
}
