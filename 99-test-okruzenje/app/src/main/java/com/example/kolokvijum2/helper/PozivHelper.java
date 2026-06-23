package com.example.kolokvijum2.helper;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 29-poziv-telefon/ */
public class PozivHelper {

    public static String uzmiBrojPrvogKontakta(AppCompatActivity activity) {
        Cursor cursor = activity.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                null, null,
                ContactsContract.CommonDataKinds.Phone._ID + " ASC LIMIT 1"
        );
        if (cursor != null && cursor.moveToFirst()) {
            String broj = cursor.getString(0);
            cursor.close();
            return broj;
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public static void otvoriDialer(AppCompatActivity activity, String broj) {
        if (broj == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + broj));
        activity.startActivity(intent);
    }

    public static void otvoriDialerPrvogKontakta(AppCompatActivity activity) {
        otvoriDialer(activity, uzmiBrojPrvogKontakta(activity));
    }
}
