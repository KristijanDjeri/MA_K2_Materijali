# Interni fajl – čuvanje teksta

**Dodatni segment.** **Slično:** SharedPreferences (zadatak 9).

**Cilj:** Sačuvaj tekst u fajl `podaci.txt` u internom skladištu aplikacije.

---

## Gde nalepiti kod

| Korak | Fajl | Gde tačno |
|-------|------|-----------|
| 1 | **`InterniFajlHelper.java`** | Novi fajl → `app/.../helper/` (kopiraj iz ovog foldera) |
| 2 | `MainActivity.java` | Na klik ili u `SwitchPostsHelper` OFF toku: `InterniFajlHelper.sacuvaj(this, textView.getText().toString());` |
| 3 | `MainActivity.java` | Pri pokretanju (opciono): `String s = InterniFajlHelper.ucitaj(this);` |

---

## Kompletan kod – helper klasa

Kopiraj **`InterniFajlHelper.java`** iz ovog foldera u `app/.../helper/`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.InterniFajlHelper;
```

### Čuvanje i čitanje

```java
InterniFajlHelper.sacuvaj(this, textView.getText().toString());
String ucitano = InterniFajlHelper.ucitaj(this);
if (!ucitano.isEmpty()) {
    textView.setText(ucitano);
}
```

> **Alternativa:** inline `FileOutputStream` ispod.

---

## Alternativa: inline u `MainActivity.java`

### Importi

```java
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
```

### Čuvanje

```java
private void sacuvajUTxtFajl(String tekst) {
    try (FileOutputStream fos = openFileOutput("podaci.txt", MODE_PRIVATE)) {
        fos.write(tekst.getBytes(StandardCharsets.UTF_8));
        Toast.makeText(this, "Sačuvano u fajl", Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
        Toast.makeText(this, "Greška pisanja", Toast.LENGTH_SHORT).show();
    }
}
```

### Čitanje

```java
private String ucitajIzTxtFajla() {
    StringBuilder sb = new StringBuilder();
    try (FileInputStream fis = openFileInput("podaci.txt");
         BufferedReader reader = new BufferedReader(
                 new InputStreamReader(fis, StandardCharsets.UTF_8))) {
        String linija;
        while ((linija = reader.readLine()) != null) {
            sb.append(linija);
        }
    } catch (Exception e) {
        return "";
    }
    return sb.toString();
}
```

### Primer poziva (umesto ili pored SharedPreferences)

```java
private void obradiSwitchOff() {
    String tekst = textView.getText().toString();
    prefs.edit().putString("tekst", tekst).apply();
    sacuvajUTxtFajl(tekst);
    postaviImePrvogKontakta();
}
```

### Učitavanje pri startu (opciono)

```java
String izFajla = ucitajIzTxtFajla();
if (!izFajla.isEmpty()) {
    // pažnja: lokacija može pregaziti ovaj tekst
}
```

---

## Alternativa

- `getExternalFilesDir(null)` – eksterni storage, i dalje privatno za app
- SharedPreferences – jednostavnije za mali tekst

---

## Checklist

- [ ] `openFileOutput` / `openFileInput`
- [ ] Nema posebne Manifest dozvole za interni fajl
