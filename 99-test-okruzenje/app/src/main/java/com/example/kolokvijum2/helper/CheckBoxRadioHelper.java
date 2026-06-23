package com.example.kolokvijum2.helper;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/** Folder: 55-checkbox-radiobutton/ */
public class CheckBoxRadioHelper {

    public CheckBoxRadioHelper(CheckBox checkBox, RadioGroup radioGroup,
                               TextView textView, int radioPoId, int radioPoTitle) {
        checkBox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) ->
                textView.setText(isChecked ? "Notifikacije: uključene" : "Notifikacije: isključene"));

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioPoId) {
                textView.setText("Sortiranje: po ID");
            } else if (checkedId == radioPoTitle) {
                textView.setText("Sortiranje: po naslovu");
            }
        });
    }

    public static boolean isNotifikacijeUkljucene(CheckBox checkBox) {
        return checkBox.isChecked();
    }

    public static boolean isSortPoNaslovu(RadioButton radioPoTitle) {
        return radioPoTitle.isChecked();
    }
}
