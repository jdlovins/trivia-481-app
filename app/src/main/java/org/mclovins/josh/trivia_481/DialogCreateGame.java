package org.mclovins.josh.trivia_481;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Josh on 1/30/18.
 */

public class DialogCreateGame extends AppCompatDialogFragment {

    @BindView(R.id.input_layout_username)
    TextInputLayout tiUsername;
    @BindView(R.id.input_username)
    EditText etUsername;
    @BindView(R.id.sbTime)
    DiscreteSeekBar sbTime;
    @BindView(R.id.sbRounds)
    DiscreteSeekBar sbRounds;
    @BindView(R.id.sbPlayers)
    DiscreteSeekBar sbPlayers;
    private CreateGameListener listener;


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
        etUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    CheckForm();
                    return true;
                }
                return false;
            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CreateGameListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(ex.toString() + " Must implement CreateGameListener");
        }
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

    private void CheckForm() {
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (etUsername.getText().toString().trim().isEmpty()) {
            tiUsername.setError("Please set a username");
        } else {
            tiUsername.setErrorEnabled(false);
            alertDialog.dismiss();
            listener.CreateGame(etUsername.getText().toString(), sbTime.getProgress(),
                    sbRounds.getProgress(), sbPlayers.getProgress());
        }
    }

    public interface CreateGameListener {
        void CreateGame(String Username, int Time, int Rounds, int Players);
    }
}
