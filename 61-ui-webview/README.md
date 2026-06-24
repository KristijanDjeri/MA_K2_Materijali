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

## Kompletan kod – helper klasa

Kopiraj **`WebViewHelper.java`** iz ovog foldera u `app/.../helper/WebViewHelper.java`.

---

## MainActivity – samo povezivanje (preporučeno)

### Import

```java
import android.widget.WebView;
import com.example.kolokvijum2.helper.WebViewHelper;
```

### U `onCreate`

```java
WebView webView = findViewById(R.id.webView);
WebViewHelper webViewHelper = new WebViewHelper(this, webView);
webViewHelper.ucitajUrl("https://dummy-json.mock.beeceptor.com/posts");
```


---

## Alternativa: inline implementacija u MainActivity

> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** — polja, metode i lifecycle pozivi.

```java
// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.webkit.WebView;
import android.webkit.WebSettings;

// U onCreate():
WebView webView = findViewById(R.id.webView);
WebSettings settings = webView.getSettings();
settings.setJavaScriptEnabled(true);
webView.loadUrl("https://app.beeceptor.com/mock-server/dummy-json");

// Implicitni Intent – otvaranje u browseru (alternativa):
// Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"));
// startActivity(browser);
```

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
