package com.example.kolokvijum2.helper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class KontaktiHelper {

    public static final int REQ_CONTACTS = 102;

    private final AppCompatActivity activity;
    private final TextView textView;

    public KontaktiHelper(AppCompatActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    public void postaviImePrvogKontakta() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQ_CONTACTS);
            return;
        }
        ucitajPrviKontakt();
    }

    public void ucitajPrviKontakt() {
        Cursor cursor = activity.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                null, null,
                ContactsContract.Contacts._ID + " ASC LIMIT 1"
        );
        if (cursor != null && cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            textView.setText(cursor.getString(idx));
            cursor.close();
        } else {
            textView.setText("Nema kontakata");
            if (cursor != null) cursor.close();
        }
    }

    public void onPermissionGranted(int requestCode, int[] grantResults) {
        if (requestCode == REQ_CONTACTS
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ucitajPrviKontakt();
        }
    }
}
