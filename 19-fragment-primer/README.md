# Fragment – gotov primer (HomeFragment)

**Dodatni segment.** **Slično:** cela logika iz zvaničnih zadataka, ali u **Fragment** umesto samo u Activity.

**Cilj:** `MainActivity` drži `HomeFragment` koji prikazuje Toast sa brojem postova u bazi (možeš zameniti bilo kojom logikom iz segmenta 02–14).

Detaljnije adaptacija: **`90-fragments-prirucnik/`** · gotov kod: **`19-fragment-primer/`**

---

## Gradle

U `app/build.gradle` → `dependencies`:

```gradle
implementation 'androidx.fragment:fragment:1.6.2'
```

(AppCompat već uključuje fragment podršku – ova linija je eksplicitna za kolokvijum.)

---

## Fajlovi u projektu

| Fajl | Putanja |
|------|---------|
| `fragment_home.xml` | `res/layout/fragment_home.xml` |
| `activity_main_host.xml` | `res/layout/activity_main.xml` (zameni host verziju) |
| `HomeFragment.java` | `.../HomeFragment.java` |
| `MainActivity.java` | host – učitava fragment |

Gotovi fajlovi u ovom folderu: `fragment_home.xml`, `activity_main_host.xml`, `HomeFragment.java`, `MainActivityHost.java`.

---

## 1. `res/layout/fragment_home.xml`

Kopiraj iz `fragment_home.xml` u ovom folderu (Switch + Button + TextView – kao osnovni layout).

---

## 2. `res/layout/activity_main.xml` (host)

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fragmentContainer"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

---

## 3. `HomeFragment.java` (ceo fajl)

Vidi `HomeFragment.java` u ovom folderu. Logika:
- `postDao` iz Room-a
- klik na dugme → Toast sa `postDao.count()`

---

## 4. `MainActivity.java` (host)

```java
package com.example.kolokvijum2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }
    }
}
```

---

## Kako prebaciti bilo koji segment u Fragment

1. Kopiraj layout u `fragment_*.xml`
2. Premesti `onCreate` logiku u `onViewCreated`
3. `findViewById` → `view.findViewById`
4. `this` / `getContext()` → `requireContext()`
5. `Toast.makeText(this, ...)` → `Toast.makeText(requireContext(), ...)`

Tabela zamena: `90-fragments-prirucnik/`.

---

## Checklist

- [ ] `fragmentContainer` u activity layoutu
- [ ] `HomeFragment` učitan u `onCreate`
- [ ] Rotacija ekrana – fragment se ne duplira (`savedInstanceState == null`)
- [ ] Room / Retrofit rade iz fragmenta

---

## Povezano

- Priručnik: `90-fragments-prirucnik/`
- Spajanje zadataka: `16-spajanje-zadataka/`
