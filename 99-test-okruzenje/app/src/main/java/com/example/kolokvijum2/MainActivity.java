package com.example.kolokvijum2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.kolokvijum2.api.RetrofitClient;
import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.model.Post;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int REQ_LOCATION = 100;
    private static final int REQ_CAMERA = 101;
    private static final int REQ_CONTACTS = 102;
    private static final String CHANNEL_ID = "posts_channel";
    private static final int NOTIF_ID = 1;

    private TextView textView;
    private ImageButton imageButton;
    private ImageView imageView;
    private Switch switchPosts;
    private Button button;

    private FusedLocationProviderClient fusedLocationClient;
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;
    private float gyroX, gyroY, gyroZ;

    private PostDao postDao;
    private boolean postsUcitani = false;
    private SharedPreferences prefs;

    private final ActivityResultLauncher<Void> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    prikaziZiroskopToast();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView);
        switchPosts = findViewById(R.id.switchPosts);
        button = findViewById(R.id.button);

        prefs = getSharedPreferences("kolokvijum_prefs", MODE_PRIVATE);
        postDao = AppDatabase.getInstance(this).postDao();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        kreirajNotificationChannel();
        pokreniLokaciju();

        imageButton.setOnClickListener(v -> pokreniKameru());
        button.setOnClickListener(v -> obrisiPrviPost());
        switchPosts.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                obradiSwitchOn();
            } else {
                obradiSwitchOff();
            }
        });
    }

    // --- Zadatak 3: Geolokacija ---

    private void pokreniLokaciju() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION);
            return;
        }
        ucitajLokaciju();
    }

    private void ucitajLokaciju() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        textView.setText("Širina: " + location.getLatitude()
                                + ", Dužina: " + location.getLongitude());
                    } else {
                        textView.setText("Lokacija nije dostupna");
                    }
                });
    }

    // --- Zadatak 4: Kamera ---

    private void pokreniKameru() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
            return;
        }
        takePictureLauncher.launch(null);
    }

    // --- Zadatak 4: Žiroskop ---

    private void prikaziZiroskopToast() {
        Toast.makeText(this,
                "Žiroskop X: " + gyroX + ", Y: " + gyroY + ", Z: " + gyroZ,
                Toast.LENGTH_SHORT).show();
    }

    // --- Zadatak 6: Switch ON ---

    private void obradiSwitchOn() {
        if (!postsUcitani) {
            ucitajPostoveSaApi();
        } else {
            Post prvi = postDao.getFirst();
            if (prvi != null) {
                Toast.makeText(this, prvi.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ucitajPostoveSaApi() {
        RetrofitClient.getApi().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> svi = response.body();
                    int n = Math.min(10, svi.size());
                    postDao.insertAll(svi.subList(0, n));
                    postsUcitani = true;
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "API greška", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- Zadatak 9: Switch OFF ---

    private void obradiSwitchOff() {
        prefs.edit().putString("tekst", textView.getText().toString()).apply();
        postaviImePrvogKontakta();
    }

    private void postaviImePrvogKontakta() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQ_CONTACTS);
            return;
        }
        ucitajPrviKontakt();
    }

    private void ucitajPrviKontakt() {
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                null, null,
                ContactsContract.Contacts._ID + " ASC LIMIT 1"
        );
        if (cursor != null && cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            textView.setText(cursor.getString(idx));
            cursor.close();
        } else {
            textView.setText("Nema kontakata");
            if (cursor != null) cursor.close();
        }
    }

    // --- Zadatak 7: Brisanje + notifikacija ---

    private void kreirajNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Postovi", NotificationManager.IMPORTANCE_DEFAULT);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }

    private void obrisiPrviPost() {
        Post prvi = postDao.getFirst();
        if (prvi != null) {
            postDao.delete(prvi);
        }
        if (postDao.count() == 0) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Obaveštenje")
                    .setContentText("Nema više postova!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat.from(this).notify(NOTIF_ID, builder.build());
        }
    }

    // --- Senzori ---

    @Override
    protected void onResume() {
        super.onResume();
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroX = event.values[0];
            gyroY = event.values[1];
            gyroZ = event.values[2];
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];
            button.setText("X: " + ax + " Y: " + ay + " Z: " + az);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // --- Dozvole ---

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        switch (requestCode) {
            case REQ_LOCATION:
                ucitajLokaciju();
                break;
            case REQ_CAMERA:
                takePictureLauncher.launch(null);
                break;
            case REQ_CONTACTS:
                ucitajPrviKontakt();
                break;
        }
    }
}
