// Implicit Intent primeri (folder 56-implicit-intent/)

import android.content.Intent;
import android.net.Uri;

private void otvoriUrl(String url) {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
}

private void podeliTekst(String tekst) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, tekst);
    startActivity(Intent.createChooser(intent, "Podeli"));
}

private void posaljiSms(String broj, String poruka) {
    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + broj));
    intent.putExtra("sms_body", poruka);
    startActivity(intent);
}

private void posaljiEmail(String adresa, String subject, String body) {
    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + adresa));
    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    intent.putExtra(Intent.EXTRA_TEXT, body);
    startActivity(intent);
}

private void otvoriMapu(double lat, double lon) {
    Uri geo = Uri.parse("geo:" + lat + "," + lon + "?q=" + lat + "," + lon);
    startActivity(new Intent(Intent.ACTION_VIEW, geo));
}
