# Kamera (zadatak 4 – deo 1)

**Cilj:** Klik na `ImageButton` → pokrene kameru → snimljena slika u `ImageView`.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Logika | `MainActivity.java` |
| Dozvola | `CAMERA` u `AndroidManifest.xml` |

## Kako napraviti

### 1. Activity Result (preporučeno, API 28+)

Koristi `ActivityResultContracts.TakePicturePreview()` – vraća `Bitmap` direktno, bez FileProvider-a (dovoljno za kolokvijum).

### 2. Alternativa – pun rezolucija

`TakePicture()` + `FileProvider` – složenije; za zadatak je dovoljna **preview** verzija.

### 3. Runtime dozvola

Pre pokretanja kamere traži `Manifest.permission.CAMERA`.

## Fajlovi

- `KameraSegment.java` – launcher, listener, dozvola

## Povezivanje sa žiroskopom

Kada se slika **zameni** u ImageView, pozovi `prikaziZiroskopToast()` iz foldera `ziroskop/`.

## Checklist

- [ ] `imageButton.setOnClickListener` → provera dozvole → `takePictureLauncher.launch(null)`
- [ ] U callback-u: `imageView.setImageBitmap(bitmap)` + Toast žiroskopa
