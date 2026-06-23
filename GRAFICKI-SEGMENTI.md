# Grafičke / UI komponente (60–68)

Segmenti za **vizuelne kontrole** u layoutu – Spinner, meni, lista, dijalog…  
**Ne mešati** sa senzorima (`41–49`) ni sa logikom podataka (`70–79`).

Helper klase: [HELPER-KLASE.md](HELPER-KLASE.md)

---

## Mapa foldera

| Folder | Komponenta | Helper klasa |
|--------|------------|--------------|
| [60-ui-recyclerview](60-ui-recyclerview/) | RecyclerView | `PostAdapter` |
| [61-ui-webview](61-ui-webview/) | WebView | `WebViewHelper` |
| [62-ui-edit-text-validacija](62-ui-edit-text-validacija/) | EditText + validacija | (inline / mali helper) |
| [63-ui-spinner](63-ui-spinner/) | Spinner | `SpinnerHelper` |
| [64-ui-date-picker](64-ui-date-picker/) | Date/Time picker | `DatePickerHelper` |
| [65-ui-progress-bar](65-ui-progress-bar/) | ProgressBar | (u OkHttp helperu) |
| [66-ui-checkbox-radiobutton](66-ui-checkbox-radiobutton/) | CheckBox, RadioButton | `CheckBoxRadioHelper` |
| [67-ui-toolbar-options-menu](67-ui-toolbar-options-menu/) | Toolbar, meni | (meni u Activity) |
| [68-ui-media-player](68-ui-media-player/) | Slika, video, audio | `MediaPlayerHelper` |

**AlertDialog** (UI dijalog) zvanično: [17-alert-dialog](17-alert-dialog/) · `AlertDialogHelper`

**Switch** zvanično: [09-switch-listener](09-switch-listener/) · `SwitchPostsHelper`

---

## Razlika: UI vs senzor vs logika

| Pitanje | Gde tražiš |
|---------|------------|
| Padajuća lista, checkbox, toolbar, media? | **60–68** (ovde) |
| Žiroskop, magnetometar, shake? | **41–49** ili **04, 12** |
| Retrofit, Room, Intent, fajl, mapa? | **70–79** |
| Alarm, mikrofon, vibracija, provider? | **80–85** |

---

## Redosled učenja (UI)

1. [63-ui-spinner](63-ui-spinner/) – jednostavan listener
2. [66-ui-checkbox-radiobutton](66-ui-checkbox-radiobutton/)
3. [62-ui-edit-text-validacija](62-ui-edit-text-validacija/)
4. [60-ui-recyclerview](60-ui-recyclerview/) – lista postova
5. [67-ui-toolbar-options-menu](67-ui-toolbar-options-menu/)
6. [68-ui-media-player](68-ui-media-player/) – slika, video, audio

Puna enumeracija: [KONVENCIJA-FOLDERA.md](KONVENCIJA-FOLDERA.md)
