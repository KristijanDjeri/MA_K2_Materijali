# Priručnik: adaptacija na Fragmente

Profesor može tražiti **Fragment** umesto cele logike u `MainActivity`.  
Ovaj priručnik te uči **kako da prilagodiš** postojeći segment – ne pokriva svaku moguću varijantu, već obrazac koji radi za većinu zadataka.

---

## 1. Activity vs Fragment – šta se menja

| U segmentima (Activity) | U Fragment verziji |
|------------------------|-------------------|
| `extends AppCompatActivity` | `extends Fragment` |
| `setContentView(R.layout....)` | `return inflater.inflate(..., container, false)` u `onCreateView` |
| `findViewById` u `onCreate` | `view.findViewById` u `onCreateView` ili `onViewCreated` |
| `getContext()` / `this` za Toast | `requireContext()` |
| `requestPermissions` | `requestPermissions` na Fragmentu (isto) |
| `onResume` / `onPause` | **Isto** – Fragment ima svoj životni ciklus |
| `registerForActivityResult` | Radi u Fragmentu – pozovi u `onCreate` |

**Šta se NE menja:** Retrofit, Room, MediaRecorder, SensorManager logika – samo je **premestiš** u Fragment klasu.

---

## 2. Minimalni šablon Fragmenta

Kreiraj npr. `HomeFragment.java`:

```java
package com.example.kolokvijum2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private TextView textView;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.button);

        // OVDE nalepiš logiku iz segmenta (onCreate deo MainActivity-ja)
    }
}
```

Layout: kopiraj `activity_main.xml` u `fragment_home.xml` (isti sadržaj, drugo ime).

---

## 3. MainActivity sa Fragmentom (host)

`MainActivity` postaje „kutija" – samo drži fragment:

```java
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

`activity_main.xml` (host):

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fragmentContainer"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**Gradle:** u `dependencies` dodaj:

```gradle
implementation 'androidx.fragment:fragment:1.6.2'
```

(U većini novih projekata već postoji preko appcompat-a.)

---

## 4. Mapa: segment → gde ide u Fragmentu

| Segment | Šta kopiraš | Gde u Fragmentu |
|---------|-------------|-----------------|
| [01-osnovni-projekat](../01-osnovni-projekat/) | Layout komponenti | `fragment_*.xml` |
| [02-geo-lokacija](../02-geo-lokacija/) | Metode lokacije | `onViewCreated` + polja |
| [03-kamera](../03-kamera/) | `ActivityResultLauncher` | Polje u Fragmentu, registrovano u `onCreate` |
| [04-senzor-ziroskop](../04-senzor-ziroskop/) | `ZiroskopHelper` | `onResume`/`onPause` u Fragmentu |
| [05-room-baza](../05-room-baza/) | Room klase | **Ostaju iste**; u Fragmentu samo poziv |
| [06-retrofit-get](../06-retrofit-get/) | API interfejs + client | Isto |
| [07-ucitaj-10-postova](../07-ucitaj-10-postova/) | `PostRepository` | `onViewCreated` |
| [08-toast-prvi-post](../08-toast-prvi-post/) | `PostRepository.prikaziTitlePrvogPosta()` | Dugme ili Switch |
| [09-switch-listener](../09-switch-listener/) | `SwitchPostsHelper` | `onViewCreated` |
| [10-brisanje-prvog-posta](../10-brisanje-prvog-posta/) | `PostRepository.obrisiPrviPost()` | Klik na dugme |
| [11-notifikacija-prazna-baza](../11-notifikacija-prazna-baza/) | Notifikacija | `requireContext()` umesto `this` |
| [12-senzor-akcelerometar](../12-senzor-akcelerometar/) | `AkcelerometarHelper` | `onResume`/`onPause` |
| [13-shared-preferences](../13-shared-preferences/) | `SharedPreferencesHelper` | `requireContext().getSharedPreferences(...)` |
| [14-kontakti](../14-kontakti/) | `KontaktiHelper` | `requireContext().getContentResolver()` |
| [60-ui-recyclerview](../60-ui-recyclerview/) | `RecyclerViewPostsHelper` | `view.findViewById(R.id.recyclerView)` |

---

## 5. Zamene koda (copy-paste pravila)

Kad prebacuješ iz `MainActivity` u `Fragment`, uradi find & replace:

| U MainActivity | U Fragmentu |
|--------------|-------------|
| `this` (za Context) | `requireContext()` |
| `getApplicationContext()` | `requireContext().getApplicationContext()` |
| `Toast.makeText(this,` | `Toast.makeText(requireContext(),` |
| `findViewById` | `view.findViewById` (u `onViewCreated`) |
| `getSharedPreferences("x", MODE_PRIVATE)` | `requireContext().getSharedPreferences("x", MODE_PRIVATE)` |
| `getSystemService(...)` | `requireContext().getSystemService(...)` |
| `getContentResolver()` | `requireContext().getContentResolver()` |
| `new File(getExternalFilesDir(null), ...)` | `new File(requireContext().getExternalFilesDir(null), ...)` |

**Ne menjaj:** imena metoda, Retrofit callback-ove, SQL u DAO-u, strukturu modela.

---

## 6. Dozvole u Fragmentu

Isto kao u Activity:

```java
if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {
    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
}
```

`onRequestPermissionsResult` ide u **Fragment** (ne u Activity), osim ako Activity prosleđuje – za kolokvijum drži sve u Fragmentu.

---

## 7. Više fragmenta (ako traže)

Tipična podela za kolokvijum:

| Fragment | Sadržaj |
|----------|---------|
| `HomeFragment` | TextView, Switch, Button, lokacija |
| `MediaFragment` | Kamera, ImageView, audio |
| `PostsFragment` | RecyclerView, Room, Retrofit |

Navigacija dugmetom:

```java
getParentFragmentManager()
    .beginTransaction()
    .replace(R.id.fragmentContainer, new PostsFragment())
    .addToBackStack(null)
    .commit();
```

Ili **BottomNavigationView** + tri fragmenta – za kolokvijum retko, dovoljna je `replace`.

---

## 8. RecyclerView u Fragmentu

Iz [60-ui-recyclerview](../60-ui-recyclerview/):

```java
@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    RecyclerView rv = view.findViewById(R.id.recyclerView);
    rv.setLayoutManager(new LinearLayoutManager(requireContext()));
    postAdapter = new PostAdapter();
    rv.setAdapter(postAdapter);
    recyclerViewPostsHelper = new RecyclerViewPostsHelper(rv, postRepository);
}
```

`PostAdapter`, `RecyclerViewPostsHelper`, `item_post.xml` – kopiraj iz segmenata.

---

## 9. Senzori u Fragmentu

Iz [04-senzor-ziroskop](../04-senzor-ziroskop/) i [12-senzor-akcelerometar](../12-senzor-akcelerometar/) – koristi **helper**, ne `SensorEventListener` na Fragmentu:

```java
private ZiroskopHelper ziroskopHelper;

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ziroskopHelper = new ZiroskopHelper(requireContext());
}

@Override
public void onResume() {
    super.onResume();
    if (ziroskopHelper != null) ziroskopHelper.onResume();
}

@Override
public void onPause() {
    super.onPause();
    if (ziroskopHelper != null) ziroskopHelper.onPause();
}
```

**Važno:** `onResume`/`onPause` na **Fragmentu**, ne u Activity – inače senzor radi i kad fragment nije vidljiv.

---

## 10. ViewModel (ako spomenu – opciono)

Ako profesor kaže „MVVM" ili „ViewModel":

- **Podaci** (Room, Retrofit) mogu u `ViewModel`
- **UI** (Toast, notifikacija, kamera) ostaje u Fragmentu

Za kolokvijum često **ne traže** ViewModel – dovoljan je Fragment sa logikom iz segmenta.

---

## 11. Brzi plan na ispitu (5 koraka)

1. Napravi `activity_main.xml` sa `FrameLayout` `fragmentContainer`
2. Napravi `fragment_home.xml` = kopija starog `activity_main` sadržaja
3. Kreiraj `HomeFragment` – nalepi polja i metode iz segmenta
4. Zameni `this` → `requireContext()`, `findViewById` → `view.findViewById`
5. U `MainActivity` samo `replace(R.id.fragmentContainer, new HomeFragment())`

Ako ne stigneš: **ostavi MainActivity** – segmenti i dalje važe; profesor često prihvata funkcionalno rešenje.

---

## 12. Checklist

- [ ] `androidx.fragment` u Gradle
- [ ] Layout fragmenta (ne mešaj sa activity_main host-om)
- [ ] `onViewCreated` za `findViewById`
- [ ] `requireContext()` za Context
- [ ] Senzori: `onResume`/`onPause` u Fragmentu
- [ ] `ActivityResultLauncher` u Fragment `onCreate`

---

## Povezano

- Konvencija foldera: [KONVENCIJA-FOLDERA.md](../KONVENCIJA-FOLDERA.md)
- Druga aktivnost (alternativa fragmentima): [70-intent-druga-aktivnost](../70-intent-druga-aktivnost/)
