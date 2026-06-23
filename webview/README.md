# WebView

**Slično:** Retrofit (internet sadržaj), ali prikaz stranice u aplikaciji.

**Mogući zadatak:** `WebView` učitava URL sajta ili API dokumentaciju.

## Gde u projektu

| Šta | Putanja |
|-----|---------|
| Layout | `activity_main.xml` ili poseban layout |
| Kod | `MainActivity.java` |
| Manifest | `android:usesCleartextTraffic="true"` samo ako je HTTP (ne HTTPS) |

## Koraci

1. U layout dodaj `WebView` sa `id/webView`
2. `WebSettings.setJavaScriptEnabled(true)` – samo ako treba JS
3. `webView.loadUrl("https://...")`

## Fajlovi

- `WebViewSegment.java`
- `webview_layout_snippet.xml`

## Checklist

- [ ] `INTERNET` dozvola (već u manifestu)
- [ ] `findViewById(R.id.webView)`
- [ ] `loadUrl(url)`

## Napomena

Za otvaranje linka u **spoljašnjem browseru** koristi implicitni Intent:

```java
Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
startActivity(i);
```
