package hacksilesia.europejskiednidziedzictwa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.EddystoneScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.IBeaconScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.discovery.BluetoothDeviceEvent;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;
import com.kontakt.sdk.android.common.*;
import com.kontakt.sdk.android.common.log.LogLevel;
import com.kontakt.sdk.android.common.profile.DeviceProfile;
import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;
import com.kontakt.sdk.android.manager.KontaktProximityManager;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class QuestABC extends AppCompatActivity implements ProximityManager.ProximityListener {
    private static final String TAG = "BEACONY";
    private ProximityManagerContract proximityManager;
    private ScanContext scanContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_abc);
        KontaktSDK.initialize("RGfcELVJXbOBOnjBuuxRMKeflkEyqEfs");
        proximityManager = new KontaktProximityManager(this);
    }

    public void onGood(View view)
    {

    }

    public void onBad(View view)
    {

    }

    @Override
    protected void onStart() {
        super.onStart();
        proximityManager.initializeScan(getScanContext(), new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.attachListener(QuestABC.this);
            }

            @Override
            public void onConnectionFailure() {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        proximityManager.detachListener(this);
        proximityManager.disconnect();
    }

    private ScanContext getScanContext() {
        if (scanContext == null) {
            scanContext = new ScanContext.Builder()
                    .setScanPeriod(ScanPeriod.RANGING) // or for monitoring for 15 seconds scan and 10 seconds waiting:
                            //.setScanPeriod(new ScanPeriod(TimeUnit.SECONDS.toMillis(15), TimeUnit.SECONDS.toMillis(10)))
                    .setScanMode(ProximityManager.SCAN_MODE_BALANCED)
                    .setActivityCheckConfiguration(ActivityCheckConfiguration.MINIMAL)
                    .setForceScanConfiguration(ForceScanConfiguration.MINIMAL)
                    .setIBeaconScanContext(new IBeaconScanContext.Builder().build())
                    .setEddystoneScanContext(new EddystoneScanContext.Builder().build())
                    .setForceScanConfiguration(ForceScanConfiguration.MINIMAL)
                    .build();
        }
        return scanContext;
    }

    @Override
    public void onEvent(BluetoothDeviceEvent bluetoothDeviceEvent) {
        List<? extends RemoteBluetoothDevice> deviceList = bluetoothDeviceEvent.getDeviceList();
        long timestamp = bluetoothDeviceEvent.getTimestamp();
        DeviceProfile deviceProfile = bluetoothDeviceEvent.getDeviceProfile();
        switch (bluetoothDeviceEvent.getEventType()) {
            case SPACE_ENTERED:
                Log.d(TAG, "namespace or region entered");
                break;
            case DEVICE_DISCOVERED:
                Log.d(TAG, "found new beacon");
                break;
            case DEVICES_UPDATE:
                Log.d(TAG, "updated beacons");
                //to powinnismy sprawdzic czy to odpowiednii beacon i wysunąc menu
                //okienko
                /*
                final QuestABC that = this;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(that);
                        builder.setMessage(R.string.dialog_message)
                                .setTitle(R.string.dialog_title);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText(R.string.beacon_in_range);
                    }
                });
                break;
            case DEVICE_LOST:
                Log.d(TAG, "lost device");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText(R.string.beacon_not_in_range);
                    }
                });
                break;
            case SPACE_ABANDONED:
                //opuszczamy nasz region schowaj menu
                Log.d(TAG, "namespace or region abandoned");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView view = (TextView) findViewById(R.id.beacon_status);
                        view.setText("namespace or region abandoned");
                    }
                });
                break;
        }
    }

    @Override
    public void onScanStart() {
        Log.d(TAG, "scan started");
    }

    @Override
    public void onScanStop() {
        Log.d(TAG, "scan stopped");
    }

}
