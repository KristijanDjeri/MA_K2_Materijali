package com.example.kolokvijum2.helper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

/** Folder: 31-date-picker/ */
public class DatePickerHelper {

    private final AppCompatActivity activity;
    private final TextView textView;

    public DatePickerHelper(AppCompatActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    public void otvoriDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(activity,
                (view, godina, mesec, dan) ->
                        textView.setText(dan + "." + (mesec + 1) + "." + godina),
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void otvoriTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(activity,
                (view, sat, minut) ->
                        textView.setText(sat + ":" + String.format("%02d", minut)),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true).show();
    }
}
