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
    private MapParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parser = new MapParser(this);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
/*
        for(MapPath mp : parser.getUserHandler().mapPaths)
        {
            Log.d("PATHEEE", "path");
            PolylineOptions rectOptions = new PolylineOptions();
            Log.d("PATHEEEs", "size: " + mp.coordinates.size());
            for(MapPoint ml : mp.coordinates)
            {
                rectOptions.add(new LatLng(ml.latitude, ml.longitude));
                Log.d("PATHEEE", "coord " + ml.latitude + " " + ml.longitude);
            }
            mMap.addPolyline(rectOptions);
        }
        */
        for(MapPoint mp : parser.getUserHandler().mapLocations)
            mMap.addMarker(new MarkerOptions().position(new LatLng(mp.latitude, mp.longitude)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.35, -122.0)));
        //mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
    }
}
