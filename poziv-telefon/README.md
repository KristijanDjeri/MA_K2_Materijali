# Poziv telefona / broj iz kontakta

**Slično:** Kontakti (zadatak 9) – ContentProvider + runtime dozvole.

**Mogući zadatak:** Pročitaj broj prvog kontakta i pozovi ga (`ACTION_CALL` ili `ACTION_DIAL`).

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Čitanje broja | `MainActivity.java` |
| Dozvole | `CALL_PHONE` (za direktan poziv) |

## Manifest

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```

## Bezbednija varijanta

`Intent.ACTION_DIAL` – otvara tastaturu telefona **bez** `CALL_PHONE` dozvole.

## Fajlovi

- `PozivSegment.java`

## Checklist

- [ ] Query `ContactsContract.CommonDataKinds.Phone`
- [ ] `Intent.ACTION_DIAL` ili `ACTION_CALL`
- [ ] `Uri.parse("tel:" + broj)`
- [ ] Runtime dozvola za `CALL_PHONE` ako koristiš CALL
