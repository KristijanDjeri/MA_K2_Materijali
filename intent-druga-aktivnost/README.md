# Druga aktivnost + Intent

**Slično:** Navigacija i prenos podataka (kao SharedPreferences, ali između ekrana).

**Mogući zadatak:** Klik na dugme otvara `DetailActivity` i prosleđuje `title` prvog posta.

## Gde u projektu

| Fajl | Putanja |
|------|---------|
| Druga aktivnost | `.../DetailActivity.java` |
| Layout | `res/layout/activity_detail.xml` |
| Manifest | registruj `<activity android:name=".DetailActivity" />` |

## Koraci

1. **File → New → Activity → Empty Activity** → `DetailActivity`
2. U layout stavi `TextView` sa `id/textDetail`
3. U `MainActivity`: `Intent intent = new Intent(this, DetailActivity.class)`
4. `intent.putExtra("naslov", vrednost)`
5. `startActivity(intent)`
6. U `DetailActivity`: `getIntent().getStringExtra("naslov")`

## Fajlovi

- `DetailActivity.java`
- `activity_detail.xml`
- `IntentSegment.java`
- `AndroidManifest-activity.xml`

## Checklist

- [ ] Eksplicitni Intent
- [ ] `putExtra` / `getStringExtra`
- [ ] Aktivnost u manifestu
