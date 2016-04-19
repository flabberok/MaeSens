package by.maesens.android.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import by.maesens.android.R;
import by.maesens.android.ui.tabs.settings.TabSettingsSecurity;

/**
 * Created by Никита on 03.04.2016.
 */
public class ChangeEmailDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.change_email));
        builder.setIcon(R.drawable.ic_email_24dp);
        builder.setMessage(getString(R.string.change_email_message));
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                    //((AlertDialog) dialog).getButton(
                      //     AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // Something into edit text. Enable the button.
                    //((AlertDialog) dialog).getButton(
                   //         AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
        builder.setView(input);

        builder
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.putExtra(TabSettingsSecurity.VAR_NEW_EMAIL, input.getText().toString());
                        getTargetFragment().onActivityResult(TabSettingsSecurity.DIALOG_FRAGMENT, TabSettingsSecurity.DIALOG_RESULT_OK, intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeEmailDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
