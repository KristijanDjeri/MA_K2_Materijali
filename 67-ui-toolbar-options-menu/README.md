# Toolbar i Options Menu

**Dodatni segment.** **Slično:** dugmad u layoutu, ali akcije u **gornjoj traci** ili **overflow meniju** (tri tačke).

**Cilj:** Meni sa stavkama „Osveži“, „Obriši“, „Podešavanja“ – svaka pokreće metodu.

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `main_menu.xml` | `res/menu/main_menu.xml` |
| `activity_main.xml` | dodaj `Toolbar` |
| `MainActivity.java` | `setSupportActionBar`, `onCreateOptionsMenu` |

Gotovi: `main_menu.xml`, `toolbar_layout_snippet.xml`, `ToolbarMenuSegment.java`.

---

## 1. Theme – AppCompat sa ActionBar

U `res/values/themes.xml` (ili pri kreiranju projekta):

```xml
<style name="Theme.Kolokvijum2" parent="Theme.AppCompat.Light.DarkActionBar">
    ...
</style>
```

Ili koristi **Toolbar** u layoutu (preporučeno):

---

## 2. Layout – Toolbar na vrhu

```xml
<androidx.appcompat.widget.Toolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary"
    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" />
```

Stavi **iznad** ostalog sadržaja u `LinearLayout` (vertical).

---

## 3. `res/menu/main_menu.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res/android/apk/res-auto">

    <item
        android:id="@+id/action_refresh"
        android:title="Osveži"
        android:icon="@android:drawable/ic_popup_sync"
        app:showAsAction="ifRoom" />

    <item
        android:id="@+id/action_delete"
        android:title="Obriši prvi"
        app:showAsAction="never" />

    <item
        android:id="@+id/action_settings"
        android:title="Podešavanja"
        app:showAsAction="never" />

</menu>
```

---

## 4. `MainActivity.java`

### Importi

```java
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
```

### U `onCreate`

```java
Toolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
if (getSupportActionBar() != null) {
    getSupportActionBar().setTitle("Kolokvijum2");
}
```

### Meni

```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_refresh) {
        postRepository.ucitajPostoveSaApi(new PostRepository.OnApiDoneListener() {
            @Override
            public void onSuccess(int count) {
                Toast.makeText(MainActivity.this, "Učitano " + count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    } else if (id == R.id.action_delete) {
        AlertDialogHelper.potvrdiBrisanje(this, () ->
                postRepository.obrisiPrviPost(
                        () -> NotifikacijaHelper.posaljiPraznaBaza(this)
                )
        );
        return true;
    } else if (id == R.id.action_settings) {
        Toast.makeText(this, "Podešavanja", Toast.LENGTH_SHORT).show();
        return true;
    }
    return super.onOptionsItemSelected(item);
}
```

Potrebni importi: `PostRepository`, `AlertDialogHelper`, `NotifikacijaHelper` (već iz segmenata 07, 11, 17).

---

## `showAsAction`

| Vrednost | Značenje |
|----------|----------|
| `ifRoom` | Ikona u toolbaru ako ima mesta |
| `always` | Uvek u toolbaru |
| `never` | U overflow meniju (tri tačke) |

---

## Checklist

- [ ] `main_menu.xml` kreiran
- [ ] `setSupportActionBar(toolbar)`
- [ ] `onCreateOptionsMenu` + `onOptionsItemSelected`
- [ ] Svaka stavka vraća `true` kad je obrađena

---

## Povezano

- Alert pre brisanja: `17-alert-dialog/`
- Fragment: meni u Activity hostu ili `setHasOptionsMenu(true)` u Fragmentu (stariji API)
