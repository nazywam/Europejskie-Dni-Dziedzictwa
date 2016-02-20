package hacksilesia.europejskiednidziedzictwa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import hacksilesia.europejskiednidziedzictwa.beacons.Beacon;
import hacksilesia.europejskiednidziedzictwa.beacons.BeaconCallbacks;

public class QuestABC extends AppCompatActivity {

    Beacon beacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_abc);

        beacon = new Beacon(this, new BeaconCallbacks() {
            @Override
            public void onBeaconUpdate() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText("beacon update");
                    }
                });
            }

            @Override
            public void onSpaceAbandoned() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText("namespace or region abandoned");
                    }
                });
            }

            @Override
            public void onDeviceLost() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText("device lost");
                    }
                });
            }

            @Override
            public void onDeviceDiscovered() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText("device discovered");
                    }
                });
            }

            @Override
            public void onSpaceEntered() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText("space entered");
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        beacon.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        beacon.start();
    }

    public void onGood(View view)
    {

    }

    public void onBad(View view)
    {

    }
}
