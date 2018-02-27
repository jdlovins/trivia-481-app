package org.mclovins.josh.trivia_481.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.mclovins.josh.trivia_481.R;
import org.mclovins.josh.trivia_481.events.CreateGameEvent;
import org.mclovins.josh.trivia_481.events.JoinGameEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh on 2/27/18.
 */

public class JoinGame extends AppCompatDialogFragment {

    @BindView(R.id.input_layout_username)
    TextInputLayout tiUsername;
    @BindView(R.id.input_layout_room_name)
    TextInputLayout tiRoomName;
    @BindView(R.id.input_room_name)
    EditText etRoomName;
    @BindView(R.id.input_username)
    EditText etUsername;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_join_game, null);

        ButterKnife.bind(this, view);

        // Create the dialog with null listeners since we need to change it later.
        builder.setView(view)
                .setTitle("Join Game")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Join", null);


        etUsername.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(etUsername, 0);
            }
        }, 50);


        etUsername.setOnKeyListener(KeyListener);
        etRoomName.setOnKeyListener(KeyListener);

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

    private void CheckForm() {

        final AlertDialog alertDialog = (AlertDialog) getDialog();
        boolean error = false;

        if (etUsername.getText().toString().trim().isEmpty()) {
            tiUsername.setError("Please enter a username");
            error = true;
        }
        else {
            tiUsername.setErrorEnabled(false);
        }
        if (etRoomName.getText().toString().trim().isEmpty()) {
            tiRoomName.setError("Please enter a Room Name");
            error = true;
        }
        else {
            tiRoomName.setErrorEnabled(false);
        }

        if (!error) {
            alertDialog.dismiss();
            int code = Integer.parseInt(etRoomName.getText().toString());
            EventBus.getDefault().post(new JoinGameEvent(etUsername.getText().toString(), code));
        }
    }

}
