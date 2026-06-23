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
