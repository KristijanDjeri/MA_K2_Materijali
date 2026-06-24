#!/usr/bin/env python3
"""Proširuje README.md fajlove sekcijom za inline implementaciju u MainActivity."""

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent

SKIP_FOLDERS = {
    "01-osnovni-projekat",
    "05-room-baza",
    "15-main-activity-referenca",
    "16-spajanje-zadataka",
    "19-fragment-primer",
    "37-notifikacije-pregled",
    "41-senzori-pregled",
    "50-firebase",
    "51-firebase-setup",
    "54-firebase-fcm",
    "90-fragments-prirucnik",
    "99-test-okruzenje",
}

SECTION_MARKER = "## Alternativa: inline implementacija u MainActivity"

ANCHOR_HEADERS = [
    "## Kako testirati",
    "## Brzi test",
    "## Samostalni test",
    "## Objašnjenje",
    "## API odgovor",
    "## Važno",
    "## Na ispitu",
    "## Više o",
    "## Checklist",
    "## Alternativne implementacije",
    "## Alternativa",
    "## Sledeći korak",
    "## Sledeći koraci",
]

NOTE_PATTERNS = [
    re.compile(
        r">\s*\*\*Napomena:\*\* Ne implementiraj logiku u `MainActivity`[^\n]*\n?",
        re.IGNORECASE,
    ),
    re.compile(
        r">\s*Za stari inline primer pogledaj[^\n]*\n?",
        re.IGNORECASE,
    ),
    re.compile(
        r">\s*\*\*Ne piši\*\*[^\n]*\n?", re.IGNORECASE
    ),
]

# Inline sadržaj za foldere bez Segment.java (ili posebne varijante)
INLINE_OVERRIDES: dict[str, str | list[tuple[str, str]]] = {
    "06-retrofit-get": """// IMPORTI:
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// U onCreate():
button.setOnClickListener(v -> testRetrofitGet());

// METODA:

private void testRetrofitGet() {
    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful() && response.body() != null) {
                int n = response.body().size();
                Toast.makeText(MainActivity.this,
                        "API vratio " + n + " postova", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Neuspešan odgovor", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            Toast.makeText(MainActivity.this,
                    "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}""",
    "07-ucitaj-10-postova": """// IMPORTI:
import android.widget.Toast;
import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// POLJE:
private boolean postsUcitani = false;

// U onCreate(), posle postDao init:
button.setOnClickListener(v -> ucitajPostoveSaApi());

// METODA:

private void ucitajPostoveSaApi() {
    RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Post> svi = response.body();
                int n = Math.min(10, svi.size());
                postDao.insertAll(svi.subList(0, n));
                postsUcitani = true;
                Toast.makeText(MainActivity.this,
                        "Učitano " + n + " postova", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            Toast.makeText(MainActivity.this,
                    "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}""",
    "08-toast-prvi-post": """// IMPORTI:
import android.widget.Toast;
import com.example.kolokvijum2.model.Post;

// U onCreate():
button.setOnClickListener(v -> prikaziTitlePrvogPosta());

// METODA:

private void prikaziTitlePrvogPosta() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        Toast.makeText(this, prvi.getTitle(), Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Nema postova u bazi", Toast.LENGTH_SHORT).show();
    }
}""",
    "10-brisanje-prvog-posta": """// IMPORTI:
import com.example.kolokvijum2.model.Post;

// U onCreate():
button.setOnClickListener(v -> obrisiPrviPost());

// METODA (samo brisanje – bez notifikacije):

private void obrisiPrviPost() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        postDao.delete(prvi);
    }
}""",
    "11-notifikacija-prazna-baza": """// IMPORTI:
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import com.example.kolokvijum2.model.Post;

// POLJA:
private static final String CHANNEL_ID = "posts_channel";
private static final int NOTIF_ID = 1;
private static final int REQ_NOTIF = 110;

// U onCreate():
kreirajNotificationChannel();
proveriDozvoluZaNotifikacije();

// METODE:

private void kreirajNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Postovi", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }
}

private void proveriDozvoluZaNotifikacije() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIF);
        }
    }
}

private void obrisiPrviPostINotifikacija() {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        postDao.delete(prvi);
    }
    if (postDao.count() == 0) {
        posaljiNotifikacijuPraznaBaza();
    }
}

private void posaljiNotifikacijuPraznaBaza() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Obaveštenje")
            .setContentText("Nema više postova!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    NotificationManagerCompat.from(this).notify(NOTIF_ID, builder.build());
}

// Listener na dugme (spoji sa 10-brisanje-prvog-posta/):
// button.setOnClickListener(v -> obrisiPrviPostINotifikacija());""",
    "38-notifikacija-osnovna": """// IMPORTI:
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

// POLJA:
private static final String CHANNEL_ID = "posts_channel";
private static final int REQ_NOTIF = 110;

// U onCreate():
kreirajOsnovniKanal();
proveriDozvoluZaNotifikacije();

// Test – npr. dugi klik na button:
button.setOnLongClickListener(v -> {
    posaljiOsnovnuNotifikaciju("Naslov obaveštenja", "Kratak tekst poruke");
    return true;
});

// METODE:

private void kreirajOsnovniKanal() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Postovi", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }
}

private void proveriDozvoluZaNotifikacije() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIF);
        }
    }
}

private void posaljiOsnovnuNotifikaciju(String naslov, String tekst) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(naslov)
            .setContentText(tekst)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);
    NotificationManagerCompat.from(this).notify(100, builder.build());
}""",
    "39-notifikacija-akcije": """// IMPORTI:
import android.app.PendingIntent;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.kolokvijum2.NotifikacijaAkcijaReceiver;

// POLJE:
private static final int NOTIF_AKCIJE_ID = 101;

// U onCreate() – test poziv:
button.setOnLongClickListener(v -> {
    posaljiNotifikacijuSaAkcijama();
    return true;
});

// METODA:

private void posaljiNotifikacijuSaAkcijama() {
    kreirajOsnovniKanal(); // iz 38-notifikacija-osnovna/

    Intent otvoriIntent = new Intent(this, NotifikacijaAkcijaReceiver.class);
    otvoriIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OTVORI);
    PendingIntent piOtvori = PendingIntent.getBroadcast(
            this, 1, otvoriIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent obrisiIntent = new Intent(this, NotifikacijaAkcijaReceiver.class);
    obrisiIntent.setAction(NotifikacijaAkcijaReceiver.ACTION_OBRISI);
    PendingIntent piObrisi = PendingIntent.getBroadcast(
            this, 2, obrisiIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    Intent contentIntent = new Intent(this, MainActivity.class);
    PendingIntent piContent = PendingIntent.getActivity(
            this, 0, contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Postovi")
            .setContentText("Imate nepročitanih postova")
            .setContentIntent(piContent)
            .setAutoCancel(true)
            .addAction(android.R.drawable.ic_menu_view, "Otvori", piOtvori)
            .addAction(android.R.drawable.ic_menu_delete, "Obriši", piObrisi);

    NotificationManagerCompat.from(this).notify(NOTIF_AKCIJE_ID, builder.build());
}""",
    "40-notifikacija-prosirena": """// IMPORTI:
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

// U onCreate() – primeri test poziva:
button.setOnLongClickListener(v -> {
    posaljiBigTextNotifikaciju("Naslov", "Ovo je duži tekst koji se vidi kad povučeš notifikaciju...");
    return true;
});

// METODE:

private void posaljiBigTextNotifikaciju(String naslov, String dugiTekst) {
    kreirajOsnovniKanal();
    NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle()
            .bigText(dugiTekst)
            .setBigContentTitle(naslov);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(naslov)
            .setContentText("Kratki pregled…")
            .setStyle(style)
            .setAutoCancel(true);
    NotificationManagerCompat.from(this).notify(102, builder.build());
}

private void posaljiInboxNotifikaciju(String... linije) {
    kreirajOsnovniKanal();
    NotificationCompat.InboxStyle inbox = new NotificationCompat.InboxStyle()
            .setBigContentTitle("Poslednji postovi");
    for (String linija : linije) {
        inbox.addLine(linija);
    }
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle("Nova obaveštenja")
            .setContentText("Povuci za listu")
            .setStyle(inbox)
            .setAutoCancel(true);
    NotificationManagerCompat.from(this).notify(104, builder.build());
}""",
    "43-senzor-svetlosti": """// MainActivity implementira SensorEventListener (pored žiroskopa/akcelerometra)

// IMPORTI:
import android.hardware.Sensor;
import android.hardware.SensorEvent;

// POLJA:
private Sensor lightSensor;

// U onCreate():
lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

// U onResume():
if (lightSensor != null) {
    sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged() dodaj:
} else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
    textView.setText("Svetlina: " + event.values[0] + " lux");
}""",
    "44-senzor-proksimiteta": """// POLJA:
private Sensor proximitySensor;

// U onCreate():
proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

// U onResume():
if (proximitySensor != null) {
    sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
    float cm = event.values[0];
    textView.setText(cm < proximitySensor.getMaximumRange()
            ? "Blizu (" + cm + " cm)" : "Daleko");
}""",
    "45-senzor-barometar": """// POLJA:
private Sensor barometer;

// U onCreate():
barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

// U onResume():
if (barometer != null) {
    sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
    textView.setText("Pritisak: " + event.values[0] + " hPa");
}""",
    "46-senzor-koraci": """// IMPORTI:
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// POLJA:
private static final int REQ_ACTIVITY = 107;
private Sensor stepCounterSensor;
private boolean koraciRegistrovan = false;

// U onCreate():
stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

// U onResume():
pokreniKorake();

// U onPause():
if (koraciRegistrovan) {
    sensorManager.unregisterListener(this, stepCounterSensor);
    koraciRegistrovan = false;
}

// METODE:

private void pokreniKorake() {
    if (stepCounterSensor == null) return;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQ_ACTIVITY);
            return;
        }
    }
    if (!koraciRegistrovan) {
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        koraciRegistrovan = true;
    }
}

// U onRequestPermissionsResult:
// if (requestCode == REQ_ACTIVITY && grantResults[0] == PERMISSION_GRANTED) pokreniKorake();

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
    textView.setText("Koraci: " + (int) event.values[0]);
}""",
    "47-senzor-izvedeni": """// POLJA:
private Sensor gravitySensor;

// U onCreate():
gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

// U onResume():
if (gravitySensor != null) {
    sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
    float gx = event.values[0], gy = event.values[1], gz = event.values[2];
    textView.setText("Gravity X:" + gx + " Y:" + gy + " Z:" + gz);
}""",
    "48-senzor-vlage-temperature": """// POLJA:
private Sensor tempSensor;
private Sensor humiditySensor;

// U onCreate():
tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

// U onResume():
if (tempSensor != null) {
    sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
}
if (humiditySensor != null) {
    sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
}

// U onSensorChanged():
} else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
    textView.setText("Temperatura: " + event.values[0] + " °C");
} else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
    textView.setText("Vlažnost: " + event.values[0] + " %");
}""",
    "52-firebase-auth": """// IMPORTI:
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// POLJA:
private FirebaseAuth firebaseAuth;
private EditText editEmail;
private EditText editLozinka;

// U onCreate():
firebaseAuth = FirebaseAuth.getInstance();
editEmail = findViewById(R.id.editEmail);
editLozinka = findViewById(R.id.editLozinka);
Button btnRegistracija = findViewById(R.id.btnRegistracija);
Button btnPrijava = findViewById(R.id.btnPrijava);

btnRegistracija.setOnClickListener(v -> firebaseRegistracija());
btnPrijava.setOnClickListener(v -> firebasePrijava());

FirebaseUser trenutni = firebaseAuth.getCurrentUser();
if (trenutni != null) {
    Toast.makeText(this, "Ulogovan: " + trenutni.getEmail(), Toast.LENGTH_SHORT).show();
}

// METODE:

private void firebaseRegistracija() {
    String email = editEmail.getText().toString().trim();
    String lozinka = editLozinka.getText().toString().trim();
    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(lozinka)) {
        Toast.makeText(this, "Unesite email i lozinku", Toast.LENGTH_SHORT).show();
        return;
    }
    firebaseAuth.createUserWithEmailAndPassword(email, lozinka)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Registracija uspešna", Toast.LENGTH_SHORT).show();
                } else {
                    String greska = task.getException() != null
                            ? task.getException().getMessage() : "Greška";
                    Toast.makeText(this, greska, Toast.LENGTH_SHORT).show();
                }
            });
}

private void firebasePrijava() {
    String email = editEmail.getText().toString().trim();
    String lozinka = editLozinka.getText().toString().trim();
    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(lozinka)) {
        Toast.makeText(this, "Unesite email i lozinku", Toast.LENGTH_SHORT).show();
        return;
    }
    firebaseAuth.signInWithEmailAndPassword(email, lozinka)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Prijava uspešna", Toast.LENGTH_SHORT).show();
                } else {
                    String greska = task.getException() != null
                            ? task.getException().getMessage() : "Greška";
                    Toast.makeText(this, greska, Toast.LENGTH_SHORT).show();
                }
            });
}""",
    "53-firebase-firestore": """// IMPORTI:
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.kolokvijum2.model.Post;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// POLJA:
private FirebaseFirestore firestore;
private boolean firestorePostsUcitani = false;

// U onCreate():
firestore = FirebaseFirestore.getInstance();
button.setOnClickListener(v -> ucitajPostoveIzFirestore());

// METODA:

private void ucitajPostoveIzFirestore() {
    firestore.collection("posts")
            .limit(10)
            .get()
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<Post> lista = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Post p = doc.toObject(Post.class);
                        if (p != null) lista.add(p);
                    }
                    int n = Math.min(10, lista.size());
                    if (n > 0) {
                        postDao.insertAll(lista.subList(0, n));
                    }
                    firestorePostsUcitani = true;
                    Toast.makeText(this, "Učitano " + n + " postova", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Firestore greška", Toast.LENGTH_SHORT).show();
                }
            });
}

// Dodavanje posta (opciono):
private void dodajPostUFirestore(String naslov, String body) {
    Map<String, Object> post = new HashMap<>();
    post.put("id", System.currentTimeMillis());
    post.put("title", naslov);
    post.put("body", body);
    post.put("userId", 1);
    firestore.collection("posts").add(post)
            .addOnSuccessListener(ref ->
                    Toast.makeText(this, "Post dodat: " + ref.getId(), Toast.LENGTH_SHORT).show());
}""",
    "79-maps-google-osm": [
        ("OSM mapa (preporučeno)", "MapsOsmSegment.java"),
        ("Google mapa", "MapsGoogleSegment.java"),
    ],
    "42-senzor-magnetometar": [
        ("Magnetometar", "MagnetometarSegment.java"),
        ("Kompas", "KompasSegment.java"),
    ],
}


def read_segment(path: Path) -> str:
    text = path.read_text(encoding="utf-8").strip()
    # Ukloni zastarele komentare na vrhu
    lines = text.splitlines()
    while lines and (
        lines[0].startswith("// === PREPORUKA")
        or lines[0].strip() == ""
    ):
        lines.pop(0)
    return "\n".join(lines).strip()


def get_inline_content(folder: Path) -> str | None:
    name = folder.name
    if name in SKIP_FOLDERS:
        return None

    override = INLINE_OVERRIDES.get(name)
    if override is not None:
        if isinstance(override, list):
            parts = []
            for subtitle, seg_name in override:
                seg_path = folder / seg_name
                if seg_path.exists():
                    parts.append(f"### {subtitle}\n\n```java\n{read_segment(seg_path)}\n```")
            return "\n\n".join(parts) if parts else None
        return override

    segments = sorted(folder.glob("*Segment*.java"))
    if not segments:
        return None

    if len(segments) == 1:
        return read_segment(segments[0])

    parts = []
    for seg in segments:
        title = seg.stem.replace("Segment", "")
        parts.append(f"### {title}\n\n```java\n{read_segment(seg)}\n```")
    return "\n\n".join(parts)


def build_section(inline: str) -> str:
    if inline.strip().startswith("###"):
        body = inline
    else:
        body = f"```java\n{inline}\n```"
    return (
        f"\n---\n\n{SECTION_MARKER}\n\n"
        f"> **Koristi ovu varijantu** ako helper klasa ne radi ili ne želiš poseban fajl "
        f"u paketu `helper`. Sav kod ispod ide **direktno u `MainActivity.java`** "
        f"— polja, metode i lifecycle pozivi.\n\n"
        f"{body}\n"
    )


def remove_notes(content: str) -> str:
    for pattern in NOTE_PATTERNS:
        content = pattern.sub("", content)
    # Ukloni prazne linije (max 3 uzastopne → 2)
    content = re.sub(r"\n{4,}", "\n\n\n", content)
    return content


def find_insert_pos(content: str) -> int:
    positions = []
    for header in ANCHOR_HEADERS:
        idx = content.find(f"\n{header}")
        if idx != -1:
            positions.append(idx)
    if positions:
        return min(positions)
    # Fallback: pre poslednjeg ---
    last_hr = content.rfind("\n---\n")
    if last_hr != -1:
        return last_hr
    return len(content)


def update_readme(folder: Path) -> bool:
    readme = folder / "README.md"
    if not readme.exists():
        return False

    inline = get_inline_content(folder)
    if inline is None:
        return False

    content = readme.read_text(encoding="utf-8")

    if SECTION_MARKER in content:
        # Zameni postojeću sekciju
        start = content.index(SECTION_MARKER)
        end = find_insert_pos(content[start:])
        if end == 0:
            end = len(content) - start
        else:
            end += start
        content = content[:start] + build_section(inline).lstrip("---\n\n") + content[end:]
    else:
        content = remove_notes(content)
        pos = find_insert_pos(content)
        section = build_section(inline)
        content = content[:pos] + section + content[pos:]

    readme.write_text(content, encoding="utf-8")
    return True


def main():
    updated = []
    skipped = []
    for folder in sorted(ROOT.iterdir()):
        if not folder.is_dir() or folder.name.startswith("."):
            continue
        if folder.name == "scripts":
            continue
        readme = folder / "README.md"
        if not readme.exists():
            continue
        if update_readme(folder):
            updated.append(folder.name)
        elif folder.name not in SKIP_FOLDERS:
            skipped.append(folder.name)

    print(f"Ažurirano: {len(updated)} README fajlova")
    for name in updated:
        print(f"  ✓ {name}")
    if skipped:
        print(f"\nBez inline sekcije: {', '.join(skipped)}")


if __name__ == "__main__":
    main()
