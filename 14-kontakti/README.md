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

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

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
