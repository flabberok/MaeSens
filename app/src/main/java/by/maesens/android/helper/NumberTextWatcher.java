package by.maesens.android.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by Виктор on 06.04.2016.
 */
public class NumberTextWatcher implements TextWatcher {

    private DecimalFormat decimalFormatFractional;
    private DecimalFormat decimalFormat;
    private boolean hasFractionalPart;

    private EditText editText;

    private String prevNumber;

    public NumberTextWatcher(EditText editText) {
        decimalFormatFractional = new DecimalFormat("#,###.##");
        decimalFormatFractional.setDecimalSeparatorAlwaysShown(true);

        decimalFormat = new DecimalFormat("#,###");

        this.editText = editText;
        hasFractionalPart = false;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        prevNumber = s.toString();
    }

    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        try {
            int currentLength = editText.getText().length();
            int finalLength;
            int currentPosition = editText.getSelectionStart();

            String separator = String.valueOf(decimalFormatFractional.getDecimalFormatSymbols().getGroupingSeparator());
            String str = s.toString().replace(separator, "");
            String prevStr = prevNumber.replace(separator, "");

            if ((currentPosition > 0)&&(str.equals(prevStr))){
                // случай попытки удаления разделителя в середине числа
                StringBuffer stringBuffer = new StringBuffer(prevNumber);
                str = stringBuffer.delete(currentPosition - 1, currentPosition).toString().replace(separator, "");
            }

            Number number = str.length() > 0 ? decimalFormatFractional.parse(str) : 0;
            if (hasFractionalPart) {
                editText.setText(decimalFormatFractional.format(number));
            } else {
                editText.setText(decimalFormat.format(number));
            }
            finalLength = editText.getText().length();
            int sel = (currentPosition + (finalLength - currentLength));
            if (sel > 0 && sel <= editText.getText().length()) {
                editText.setSelection(sel);
            }
        } catch (NumberFormatException e) {
            Log.d("NumberTextWatcher", e.toString());
        } catch (ParseException e) {
            Log.d("NumberTextWatcher", e.toString());
        }

        editText.addTextChangedListener(this);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(String.valueOf(decimalFormatFractional.getDecimalFormatSymbols().getDecimalSeparator()))) {
            hasFractionalPart = true;
        } else {
            hasFractionalPart = false;
        }
    }

    public static int trimCommaOfString(String string) {
        if(string.contains(" "))
            return Integer.valueOf(string.replace(" ", ""));
        else
            return Integer.valueOf(string);
    }
}