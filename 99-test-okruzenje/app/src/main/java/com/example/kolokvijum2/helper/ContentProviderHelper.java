package com.example.kolokvijum2.helper;

import android.content.Context;
import android.database.Cursor;

import com.example.kolokvijum2.PostContentProvider;

/** Folder: 84-content-provider/ – čitanje preko ContentResolver-a */
public class ContentProviderHelper {

    public static String getFirstPostTitle(Context context) {
        Cursor cursor = context.getContentResolver().query(
                PostContentProvider.CONTENT_URI,
                new String[]{"title"},
                null,
                null,
                "_id ASC LIMIT 1"
        );

        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            cursor.close();
            return title;
        }
        if (cursor != null) {
            cursor.close();
        }
        return "Nema postova";
    }
}
