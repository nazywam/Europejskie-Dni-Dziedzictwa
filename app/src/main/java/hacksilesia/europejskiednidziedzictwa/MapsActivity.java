package hacksilesia.europejskiednidziedzictwa;

import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import hacksilesia.europejskiednidziedzictwa.mapsparser.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UserHandler userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapParser parser = new MapParser();
        userHandler = parser.userhandler;

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.d("PATHEEE", "size: " + userHandler.mapLocations.size());

        for(MapPath mp : userHandler.mapPaths)
        {
            Log.d("PATHEEE", "path");
            PolylineOptions rectOptions = new PolylineOptions();
            for(MapPoint ml : mp.coordinates)
            {
                rectOptions.add(new LatLng(ml.latitude, ml.longitude));
                Log.d("PATHEEE", "coord " + ml.latitude + " " + ml.longitude);
            }
            mMap.addPolyline(rectOptions);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.35, -122.0)));
    }
}
