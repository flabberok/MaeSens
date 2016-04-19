package by.maesens.android.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import by.maesens.android.R;
import by.maesens.android.ui.fragments.BalanceFragment;

/**
 * Created by Никита on 17.03.2016.
 */
public class BidDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.enter_sum));
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.putExtra(BalanceFragment.DIALOG_BID_VAL, Integer.valueOf(input.getText().toString()));
                        getTargetFragment().onActivityResult(BalanceFragment.DIALOG_FRAGMENT, BalanceFragment.DIALOG_RESULT_OK, intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BidDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
