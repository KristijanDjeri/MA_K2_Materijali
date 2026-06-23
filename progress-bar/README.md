# ProgressBar / učitavanje

**Slično:** Retrofit callback (zadatak 5) – prikaži da se nešto učitava.

**Mogući zadatak:** ProgressBar vidljiv dok traje API poziv, sakrij posle.

## Fajlovi

- `progress_layout_snippet.xml`
- `ProgressBarSegment.java`

## Checklist

- [ ] `android:visibility="gone"` na početku
- [ ] `VISIBLE` pre `enqueue`
- [ ] `GONE` u `onResponse` / `onFailure`
