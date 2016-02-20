package hacksilesia.europejskiednidziedzictwa;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RemoteViewsService;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import hacksilesia.europejskiednidziedzictwa.mapsparser.MapLocation;
import hacksilesia.europejskiednidziedzictwa.mapsparser.MapParser;
import hacksilesia.europejskiednidziedzictwa.mapsparser.MapPath;
import hacksilesia.europejskiednidziedzictwa.mapsparser.MapPoint;
import hacksilesia.europejskiednidziedzictwa.mapsparser.MapRiddle;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapParser parser;

    private View quizView;
    private View monumentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parser = new MapParser(this);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        quizView = findViewById(R.id.quiz_view);
        monumentView = findViewById(R.id.monument_view);
        quizView.setVisibility(View.GONE);
        monumentView.setVisibility(View.GONE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        for(MapLocation mp : parser.getUserHandler().mapLocations){
            AssetManager am = getAssets();
            InputStream is = null;
            try {
                is = am.open(mp.imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap icon = BitmapFactory.decodeStream(is);
            Bitmap scaled = Bitmap.createScaledBitmap(icon, 96, 96, false);
            Marker m = mMap.addMarker(new MarkerOptions().title(mp.name).icon(BitmapDescriptorFactory.fromBitmap(scaled)).snippet(mp.description).position(new LatLng(mp.longitude, mp.latitude)));
        }
        for(MapPath mp : parser.getUserHandler().mapPaths){

            PolylineOptions o = new PolylineOptions();
            o.width(10).color(0xFF00AAAA);
            for(MapPoint p : mp.coordinates){
                o.add(new LatLng(p.longitude, p.latitude));
            }
            mMap.addPolyline(o);
        }

        for(MapRiddle mr : parser.getUserHandler().mapRiddles){
            AssetManager am = getAssets();
            InputStream is = null;
            try {
               is = am.open(mr.imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap icon = BitmapFactory.decodeStream(is);
            Bitmap scaled = Bitmap.createScaledBitmap(icon, 96, 96, false);
            Marker m = mMap.addMarker(new MarkerOptions().title(mr.name).icon(BitmapDescriptorFactory.fromBitmap(scaled)).snippet(mr.description).position(new LatLng(mr.longitude, mr.latitude)));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(50.2644568, 18.9956939)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (MapRiddle mr : parser.getUserHandler().mapRiddles) {
                    if (mr.longitude == marker.getPosition().latitude &&
                            mr.latitude == marker.getPosition().longitude) {
                        showRiddle(mr);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void showRiddle(final MapRiddle riddle) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

                builder.setMessage(riddle.description)
                        .setTitle("Zagadka!");

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void onStartBtnClicked(View view) {
        View txt = findViewById(R.id.welcomeMessage);
        txt.animate().translationX(-300).alpha(0);
        view.animate().translationX(-300).alpha(0);
    }
}
