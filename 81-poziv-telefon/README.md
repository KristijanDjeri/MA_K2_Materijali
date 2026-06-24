# Poziv telefona – broj iz kontakta

**Dodatni segment.** **Slično:** čitanje kontakata (zadatak 9).

**Cilj:** Uzmi broj prvog kontakta i otvori dialer ili pozovi.

---

## Manifest (samo za direktan poziv)

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```

> Za `ACTION_DIAL` (samo otvara tastaturu) **ne treba** `CALL_PHONE`.

---

## Kompletan kod – helper klasa

Kopiraj **`PozivHelper.java`** iz ovog foldera u `app/.../helper/PozivHelper.java`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import com.example.kolokvijum2.helper.PozivHelper;
```

### Otvori dialer sa brojem prvog kontakta

```java
PozivHelper.otvoriDialerPrvogKontakta(this);
```

### Ili ručno sa brojem

```java
String broj = PozivHelper.uzmiBrojPrvogKontakta(this);
PozivHelper.otvoriDialer(this, broj);
```

> Za stari inline primer pogledaj `*Segment.java` u istom folderu.

---

> **Napomena:** Ne implementiraj logiku u `MainActivity` – kopiraj helper klasu i u `onCreate` samo pozovi njene metode. Za stari inline primer pogledaj `*Segment.java` u istom folderu.

## Alternativa

- Prikaz imena + broja u TextView umesto poziva
- `READ_CONTACTS` i dalje treba za query – već imaš iz zadatka 9

---

## Checklist

- [ ] Query na Phone.CONTENT_URI
- [ ] `tel:` URI u Intent-u
- [ ] CALL_PHONE samo ako koristiš ACTION_CALL
