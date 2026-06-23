// Google Maps (folder 58-maps-google-osm/)
// Gradle: implementation 'com.google.android.gms:play-services-maps:18.2.0'
// Manifest meta-data: com.google.android.geo.API_KEY

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng beograd = new LatLng(44.7866, 20.4489);
        googleMap.addMarker(new MarkerOptions().position(beograd).title("Beograd"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beograd, 14f));
    }
}
