package hacksilesia.europejskiednidziedzictwa;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
            o.width(10).color(0x3068FF);
            for(MapPoint p : mp.coordinates){
                o.add(new LatLng(p.longitude, p.latitude));
            }
            mMap.addPolyline(o);
        }

        for(MapRiddle mr : parser.getUserHandler().mapRiddles){
            AssetManager am = getAssets();
            InputStream is = null;
            try {
               is = am.open("quiz_icon.png");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap icon = BitmapFactory.decodeStream(is);
            Bitmap scaled = Bitmap.createScaledBitmap(icon, 96, 96, false);
            Marker m = mMap.addMarker(new MarkerOptions().title(mr.name).icon(BitmapDescriptorFactory.fromBitmap(scaled)).snippet(mr.description).position(new LatLng(mr.longitude, mr.latitude)));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(50.26785, 19.12195)));
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
                Intent intent = new Intent(MapsActivity.this, Quiz.class);
                intent.putExtra("ZAGADKA", riddle.description);
                intent.putExtra("ANS1", riddle.answer[0]);
                intent.putExtra("ANS2", riddle.answer[1]);
                intent.putExtra("ANS3", riddle.answer[2]);
                intent.putExtra("CORRECT", riddle.correctAns);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void onStartBtnClicked(View view) {
        View txt = findViewById(R.id.welcomeMessage);
        txt.animate().translationX(-300).alpha(0);
        view.animate().translationX(-300).alpha(0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                int  stredittext=data.getIntExtra("edittextvalue", 1);
                if(stredittext == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Dobra odpowiedź")
                            .setTitle("BRAWO");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Niestety, pomyliłeś się")
                            .setTitle(";(");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }

}
