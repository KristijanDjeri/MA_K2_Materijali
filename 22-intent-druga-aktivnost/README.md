# Druga aktivnost + Intent (prenos podataka)

**Dodatni segment.** **Slično:** prenos podataka kao SharedPreferences, ali na drugi ekran.

**Cilj:** Klik otvara `DetailActivity` i prosleđuje npr. `title` posta.

---

## Fajlovi

| Fajl | Putanja |
|------|---------|
| `activity_detail.xml` | `res/layout/activity_detail.xml` |
| `DetailActivity.java` | `.../DetailActivity.java` |
| Manifest unos | unutar `<application>` |
| Poziv iz MainActivity | `Intent` + `startActivity` |

---

## 1. `res/layout/activity_detail.xml` (ceo fajl)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textDetail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="18sp" />

</LinearLayout>
```

---

## 2. `DetailActivity.java` (ceo fajl)

```java
package com.example.kolokvijum2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textDetail = findViewById(R.id.textDetail);

        String naslov = getIntent().getStringExtra("naslov");
        if (naslov != null) {
            textDetail.setText(naslov);
        } else {
            textDetail.setText("Nema prosleđenih podataka");
        }
    }
}
```

---

## 3. `AndroidManifest.xml` – unutar `<application>`

```xml
<activity
    android:name=".DetailActivity"
    android:exported="false" />
```

---

## 4. U `MainActivity.java`

### Import

```java
import android.content.Intent;
```

### Metoda

```java
private void otvoriDetalj(String naslov) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra("naslov", naslov);
    startActivity(intent);
}
```

### Primer poziva (npr. dugi klik na Switch)

```java
switchPosts.setOnLongClickListener(v -> {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        otvoriDetalj(prvi.getTitle());
    }
    return true;
});
```

---

## Alternativa

- `putExtra("naslov", prvi.getId())` + slanje int-a – `getIntExtra`
- `Serializable` / `Parcelable` za ceo `Post` objekat – više koda

---

## Checklist

- [ ] DetailActivity kreirana
- [ ] Layout postoji
- [ ] Aktivnost u Manifest-u
- [ ] `putExtra` / `getStringExtra` rade
