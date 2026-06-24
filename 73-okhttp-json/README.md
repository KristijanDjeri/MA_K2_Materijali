# OkHttp – ručno čitanje JSON-a

**Dodatni segment.** **Alternativa** Retrofit-u (zadatak 5).

**Cilj:** Preuzmi JSON sa URL-a u pozadini, parsiraj Gson-om, upiši u Room.

---

## 1. Gradle (`app/build.gradle`)

```gradle
implementation 'com.squareup.okhttp3:okhttp:4.12.0'
implementation 'com.google.code.gson:gson:2.10.1'
```

Sync Now.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | `app/build.gradle` | OkHttp + Gson u `dependencies` |
| 2 | **`OkHttpHelper.java`** | Novi fajl → `app/.../helper/` |
| 3 | `MainActivity.java` | Polje + init u **`onCreate`**, poziv `ucitajPostove()` |
| 4 | `MainActivity.java` | **`onDestroy`**: `okHttpHelper.shutdown()` |

---

## Kompletan kod – helper klasa

Kopiraj **`OkHttpHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.OkHttpHelper;
```

### Polje i poziv

```java
private OkHttpHelper okHttpHelper;

// onCreate (posle postDao i progressBar):
okHttpHelper = new OkHttpHelper(this, postDao, progressBar);
okHttpHelper.ucitajPostove(
        "https://dummy-json.mock.beeceptor.com/posts",
        new OkHttpHelper.OnDoneListener() {
            @Override
            public void onSuccess(int count) {
                Toast.makeText(MainActivity.this, "Učitano " + count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
);
```

### U `onDestroy`

```java
if (okHttpHelper != null) {
    okHttpHelper.shutdown();
}
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa

- Retrofit – manje koda, preporučeno na kolokvijumu
- `HttpURLConnection` – stariji Java API

---

## Checklist

- [ ] OkHttp + Gson u Gradle
- [ ] `execute()` u pozadini
- [ ] UI na main thread
- [ ] TypeToken za `List<Post>`
