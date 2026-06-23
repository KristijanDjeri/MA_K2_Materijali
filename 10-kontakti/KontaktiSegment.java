// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// POLJE:
private static final int REQ_CONTACTS = 102;

// METODA:

private void postaviImePrvogKontakta() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                REQ_CONTACTS);
        return;
    }
    ucitajPrviKontakt();
}

private void ucitajPrviKontakt() {
  Cursor cursor = getContentResolver().query(
          ContactsContract.Contacts.CONTENT_URI,
          new String[]{ContactsContract.Contacts.DISPLAY_NAME},
          null, null,
          ContactsContract.Contacts._ID + " ASC LIMIT 1"
  );

  if (cursor != null && cursor.moveToFirst()) {
      int idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
      String ime = cursor.getString(idx);
      textView.setText(ime);
      cursor.close();
  } else {
      textView.setText("Nema kontakata");
      if (cursor != null) cursor.close();
  }
}

// U onRequestPermissionsResult dodaj REQ_CONTACTS:
// if (requestCode == REQ_CONTACTS && grantResults[0] == PERMISSION_GRANTED) {
//     ucitajPrviKontakt();
// }
