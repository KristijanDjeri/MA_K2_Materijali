// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.database.Cursor;
import com.example.kolokvijum2.PostContentProvider;
import com.example.kolokvijum2.helper.ContentProviderHelper;

// METODA – čitanje prvog posta preko sopstvenog provider-a:
private void prikaziPrviPostPrekoProvidera() {
    String title = ContentProviderHelper.getFirstPostTitle(this);
    textView.setText(title);
}

// Alternativa bez helpera (direktno u Activity):
private void prikaziPrviPostDirektno() {
    Cursor cursor = getContentResolver().query(
            PostContentProvider.CONTENT_URI,
            new String[]{"title"},
            null,
            null,
            "_id ASC LIMIT 1"
    );

    if (cursor != null && cursor.moveToFirst()) {
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        textView.setText(title);
        cursor.close();
    } else {
        textView.setText("Nema postova");
        if (cursor != null) {
            cursor.close();
        }
    }
}
