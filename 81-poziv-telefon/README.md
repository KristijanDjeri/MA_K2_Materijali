# Poziv telefona – broj iz kontakta

**Dodatni segment.** **Slično:** čitanje kontakata (zadatak 9).

**Cilj:** Uzmi broj prvog kontakta i otvori dialer ili pozovi.

---

## Manifest (samo za direktan poziv)

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```

> Za `ACTION_DIAL` (samo otvara tastaturu) **ne treba** `CALL_PHONE`.

---

## Kompletan kod – helper klasa

Kopiraj **`PozivHelper.java`** iz ovog foldera u `app/.../helper/PozivHelper.java`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.PozivHelper;
```

### Otvori dialer sa brojem prvog kontakta

```java
PozivHelper.otvoriDialerPrvogKontakta(this);
```

### Ili ručno sa brojem

```java
String broj = PozivHelper.uzmiBrojPrvogKontakta(this);
PozivHelper.otvoriDialer(this, broj);
```

> **Alternativa:** inline `ContentResolver` + `Intent` ispod.

---

## Alternativa: inline u `MainActivity.java`

### Importi

```java
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
```

### Konstanta (samo za CALL)

```java
private static final int REQ_CALL = 104;
```

### Čitanje broja prvog kontakta

```java
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
```

### Varijanta A: Dialer (bez CALL dozvole) – preporučeno

```java
private void otvoriDialerSaPrvimKontaktom() {
    String broj = uzmiBrojPrvogKontakta();
    if (broj == null) {
        Toast.makeText(this, "Nema broja", Toast.LENGTH_SHORT).show();
        return;
    }
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + broj));
    startActivity(intent);
}
```

### Varijanta B: Direktan poziv (zahteva CALL_PHONE)

```java
private void pozoviPrviKontakt() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL);
        return;
    }
    String broj = uzmiBrojPrvogKontakta();
    if (broj != null) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + broj));
        startActivity(intent);
    }
}
```

### U `onRequestPermissionsResult`

```java
} else if (requestCode == REQ_CALL) {
    pozoviPrviKontakt();
}
```

---

## Alternativa

- Prikaz imena + broja u TextView umesto poziva
- `READ_CONTACTS` i dalje treba za query – već imaš iz zadatka 9

---

## Checklist

- [ ] Query na Phone.CONTENT_URI
- [ ] `tel:` URI u Intent-u
- [ ] CALL_PHONE samo ako koristiš ACTION_CALL
