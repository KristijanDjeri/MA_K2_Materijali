# Kontakti (zadatak 9 – deo 2)

**Cilj:** Posle čuvanja u SharedPreferences, **zameni** sadržaj `TextView`-a sa **imenom prvog kontakta** iz Contacts aplikacije.

---

## Šta ti treba pre ovoga

- `13-shared-preferences/` – `SharedPreferencesHelper` (poziva `SwitchPostsHelper` na Switch OFF)
- Dozvola `READ_CONTACTS` u Manifest-u

---

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`KontaktiHelper.java`** | Novi fajl → `app/.../helper/` |
| 2 | `MainActivity.java` | Polje + init u **`onCreate`** |
| 3 | `MainActivity.java` | **`onRequestPermissionsResult`**: `kontaktiHelper.onPermissionGranted(...)` |
| 4 | `09-switch-listener/` | `SwitchPostsHelper` automatski poziva `kontaktiHelper.postaviImePrvogKontakta()` na Switch OFF |

---

## Kompletan kod – helper klasa

Kopiraj **`KontaktiHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.KontaktiHelper;
```

### Polje

```java
private KontaktiHelper kontaktiHelper;
```

### U `onCreate`

```java
kontaktiHelper = new KontaktiHelper(this, textView);
```

### Na Switch OFF

Kad u `09-switch-listener/` inicijalizuješ `SwitchPostsHelper`, kontakt se učitava automatski:

```java
// unutar SwitchPostsHelper.obradiSwitchOff():
kontaktiHelper.postaviImePrvogKontakta();
```

Ručni test (bez Switch-a):

```java
button.setOnClickListener(v -> kontaktiHelper.postaviImePrvogKontakta());
```

### U `onRequestPermissionsResult`

```java
if (kontaktiHelper != null) {
    kontaktiHelper.onPermissionGranted(requestCode, grantResults);
}
```

> **Alternativa:** inline kod ispod ili `KontaktiSegment.java`.

---

## Alternativa: inline u `MainActivity.java`

### 1. Importi

```java
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
```

### 2. Konstanta

```java
private static final int REQ_CONTACTS = 102;
```

### 3. Metoda koja proverava dozvolu

```java
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
```

### 4. Čitanje prvog kontakta iz baze kontakata

```java
private void ucitajPrviKontakt() {
    Cursor cursor = getContentResolver().query(
            ContactsContract.Contacts.CONTENT_URI,
            new String[]{ContactsContract.Contacts.DISPLAY_NAME},
            null,
            null,
            ContactsContract.Contacts._ID + " ASC LIMIT 1"
    );

    if (cursor != null && cursor.moveToFirst()) {
        int idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String ime = cursor.getString(idx);
        textView.setText(ime);
        cursor.close();
    } else {
        textView.setText("Nema kontakata");
        if (cursor != null) {
            cursor.close();
        }
    }
}
```

### 5. U `onRequestPermissionsResult` dodaj

```java
} else if (requestCode == REQ_CONTACTS) {
    ucitajPrviKontakt();
}
```

### 6. Poziv iz `obradiSwitchOff()` (ceo tok zadatka 9)

```java
private void obradiSwitchOff() {
    String trenutniTekst = textView.getText().toString();
    prefs.edit().putString("tekst", trenutniTekst).apply();
    postaviImePrvogKontakta();
}
```

---

## Kako testirati

1. Na emulatoru otvori **Contacts** aplikaciju
2. Dodaj bar jedan kontakt (npr. "Marko Marković")
3. U tvojoj app: uključi pa isključi Switch
4. TextView treba da pokaže ime tog kontakta

---

## Alternativne implementacije

| Ovaj primer | Alternativa |
|-------------|-------------|
| `DISPLAY_NAME` | `ContactsContract.CommonDataKinds.Phone` – broj telefona → folder `81-poziv-telefon/` |
| `LIMIT 1` u sort | `moveToFirst()` bez LIMIT – uzima prvi red kako ga sistem vrati |
| `getColumnIndex` | Direktno `cursor.getString(0)` ako si siguran u redosled kolona |

---

## Checklist

- [ ] `READ_CONTACTS` u Manifest-u
- [ ] Runtime dozvola
- [ ] Switch OFF: prvo SharedPreferences, pa kontakt
- [ ] TextView prikazuje ime prvog kontakta

---

## Zadatak 9 je završen

Kad su urađeni `13-shared-preferences/` i `14-kontakti/`, zadatak 9 je kompletan.
