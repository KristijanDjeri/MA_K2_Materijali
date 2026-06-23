# Kontakti (zadatak 9 – deo 2)

**Cilj:** Posle čuvanja u SharedPreferences, u `TextView` prikaži **ime prvog kontakta** iz Contacts aplikacije.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Čitanje kontakata | `MainActivity.java` |
| Dozvola | `READ_CONTACTS` u manifestu |

## Kako napraviti

1. Runtime dozvola `READ_CONTACTS`
2. `ContentResolver.query` na `ContactsContract.Contacts.CONTENT_URI`
3. Uzmi prvi red → `DISPLAY_NAME` → `textView.setText(ime)`

## Fajlovi

- `KontaktiSegment.java`

## Emulator

Dodaj kontakte: **Contacts** app → Create contact.

## Checklist

- [ ] `READ_CONTACTS` u manifestu + runtime
- [ ] `ContactsContract.Contacts.CONTENT_URI`
- [ ] Prvi kontakt (LIMIT 1 ili moveToFirst)
- [ ] Pozvati iz `obradiSwitchOff()` posle SharedPreferences
