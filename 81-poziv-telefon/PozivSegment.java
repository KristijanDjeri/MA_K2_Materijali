// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

// SAMO biranje broja (bez CALL dozvole):
private void otvoriDialerSaPrvimKontaktom() {
    String broj = uzmiBrojPrvogKontakta();
    if (broj == null) {
        Toast.makeText(this, "Nema broja", Toast.LENGTH_SHORT).show();
        return;
    }
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + broj));
    startActivity(intent);
}

// DIREKTAN poziv (zahteva CALL_PHONE):
private void pozoviPrviKontakt() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE}, 104);
        return;
    }
    String broj = uzmiBrojPrvogKontakta();
    if (broj != null) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + broj));
        startActivity(intent);
    }
}

private String uzmiBrojPrvogKontakta() {
    Cursor cursor = getContentResolver().query(
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
    if (cursor != null) cursor.close();
    return null;
}
