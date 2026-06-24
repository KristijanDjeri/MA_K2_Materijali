# ContentProvider – sopstveni provider za postove

**Dodatni segment.** **Slično:** `14-kontakti/` (čitanje preko `ContentResolver`), ali ovde **ti** izlažeš podatke iz Room baze.

**Cilj:** Napravi `PostContentProvider` koji vraća postove preko `content://` URI-ja; u `MainActivity` pročitaj prvi naslov.

---

## Četiri glavne Android komponente

| Komponenta | Uloga |
|------------|--------|
| Activity | UI ekran |
| Service | Posao u pozadini |
| **ContentProvider** | Deljenje strukturiranih podataka (`content://`) |
| **BroadcastReceiver** | Reakcija na emitovani Intent → `85-broadcast-receiver/` |

---

## Preduslovi

- `05-room-baza/` – Room model + DAO
- Postovi već u bazi (npr. posle `07-ucitaj-10-postova/`)

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `PostContentProvider.java` | `.../PostContentProvider.java` |
| `ContentProviderHelper.java` | `.../helper/ContentProviderHelper.java` |
| Manifest | `<provider>` unutar application |

---

## Kompletan kod – provider + helper

1. Kopiraj **`PostContentProvider.java`** u root paket (`com.example.kolokvijum2`).
2. Kopiraj **`ContentProviderHelper.java`** u `app/.../helper/` (**kompletan kod – helper klasa** za čitanje).

---

## MainActivity – samo povezivanje

```java
import com.example.kolokvijum2.helper.ContentProviderHelper;

button.setOnClickListener(v ->
        textView.setText(ContentProviderHelper.getFirstPostTitle(this))
);
```


## 1. `PostContentProvider.java` (ceo fajl)

Kopiraj iz `PostContentProvider.java` u ovom folderu.

Ključne stvari:

```java
public static final String AUTHORITY = "com.example.kolokvijum2.provider.posts";
public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/posts");
```

U `query()` čitaš iz `PostDao` i vraćaš `MatrixCursor`:

```java
List<Post> posts = postDao.getAll();
MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "title", "body"});
for (Post post : posts) {
    cursor.addRow(new Object[]{post.getId(), post.getTitle(), post.getBody()});
}
return cursor;
```

---

## 2. `AndroidManifest.xml` – unutar `<application>`

```xml
<provider
    android:name=".PostContentProvider"
    android:authorities="com.example.kolokvijum2.provider.posts"
    android:exported="false" />
```

> `android:exported="false"` – samo tvoja aplikacija čita provider. Za deljenje sa drugim app-ovima stavi `true` + dozvole.

---

## Poređenje sa kontaktima (zadatak 9)

| | Kontakti (`14-kontakti/`) | Sopstveni provider (ovde) |
|--|---------------------------|---------------------------|
| URI | `ContactsContract.Contacts.CONTENT_URI` | `PostContentProvider.CONTENT_URI` |
| Ko ga pruža | Sistem (Contacts app) | Tvoja aplikacija |
| API | `getContentResolver().query(...)` | Isto |

---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
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
```

## Checklist

- [ ] `PostContentProvider` extends `ContentProvider`
- [ ] `AUTHORITY` u Manifest-u = `AUTHORITY` u kodu
- [ ] `query()` vraća `Cursor`
- [ ] `ContentProviderHelper.getFirstPostTitle()` pozvan iz listenera

---

## Povezano

- Čitanje tuđeg provider-a: `14-kontakti/`
- Sopstveni provider (izlažeš podatke): ovaj folder
- Fajlovi (slike): `77-file-provider/` (`FileProvider`, drugačija namena)
- Broadcast posle izmene: `85-broadcast-receiver/`
