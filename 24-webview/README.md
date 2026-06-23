# WebView – prikaz web stranice

**Dodatni segment.** **Slično:** internet sadržaj kao Retrofit.

**Cilj:** Učitaj URL u `WebView` unutar aplikacije.

---

## 1. U `activity_main.xml` dodaj

```xml
<WebView
    android:id="@+id/webView"
    android:layout_width="match_parent"
    android:layout_height="300dp" />
```

---

## 2. U `MainActivity.java` (ceo deo)

### Importi

```java
import android.webkit.WebView;
import android.webkit.WebSettings;
```

### U `onCreate`

```java
WebView webView = findViewById(R.id.webView);
WebSettings settings = webView.getSettings();
settings.setJavaScriptEnabled(true);
webView.loadUrl("https://dummy-json.mock.beeceptor.com/posts");
```

> **Napomena:** `setJavaScriptEnabled(true)` nije uvek obavezno – stavi ako stranica ne radi bez JS.

---

## Alternativa: otvori u spoljašnjem browseru

```java
import android.content.Intent;
import android.net.Uri;

Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"));
startActivity(browser);
```

Ne treba WebView u layoutu.

---

## HTTP (ne HTTPS) stranice

U `AndroidManifest.xml` unutar `<application>`:

```xml
android:usesCleartextTraffic="true"
```

> Samo ako učitavaš `http://` URL. Za kolokvijum preferiraj HTTPS.

---

## Checklist

- [ ] `INTERNET` dozvola (već u manifestu)
- [ ] WebView u layoutu
- [ ] `loadUrl(...)` pozvan
