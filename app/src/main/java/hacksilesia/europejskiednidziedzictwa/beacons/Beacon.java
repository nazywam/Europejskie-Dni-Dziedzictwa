package hacksilesia.europejskiednidziedzictwa.beacons;

import android.app.Activity;
import android.util.Log;

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
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.DeviceProfile;
import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;
import com.kontakt.sdk.android.manager.KontaktProximityManager;

import java.util.List;

/**
 * Created by angela on 2/20/16.
 */
public class Beacon implements ProximityManager.ProximityListener {
    private static final String TAG = "BEACONY";
    private ProximityManagerContract proximityManager;
    private ScanContext scanContext;
    private BeaconCallbacks callbacks;

    public Beacon(Activity ac, BeaconCallbacks callbacks) {
        this.callbacks = callbacks;
        KontaktSDK.initialize("RGfcELVJXbOBOnjBuuxRMKeflkEyqEfs");
        proximityManager = new KontaktProximityManager(ac);
    }

    @Override
    public void onScanStart() {
        Log.d(TAG, "scan started");
    }

    @Override
    public void onScanStop() {
        Log.d(TAG, "scan stopped");
    }
    @Override
    public void onEvent(BluetoothDeviceEvent bluetoothDeviceEvent) {
        List<? extends RemoteBluetoothDevice> deviceList = bluetoothDeviceEvent.getDeviceList();
        long timestamp = bluetoothDeviceEvent.getTimestamp();
        DeviceProfile deviceProfile = bluetoothDeviceEvent.getDeviceProfile();
        switch (bluetoothDeviceEvent.getEventType()) {
            case SPACE_ENTERED:
                Log.d(TAG, "namespace or region entered");
                callbacks.onSpaceEntered();
                break;
            case DEVICE_DISCOVERED:
                Log.d(TAG, "found new beacon");
                for (RemoteBluetoothDevice i: deviceList) {
                    Log.d("Discovered "+i.getUniqueId(), "");
                }
                callbacks.onDeviceDiscovered();

                break;
            case DEVICES_UPDATE:
                Log.d(TAG, "updated beacons");
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
                callbacks.onBeaconUpdate();
                break;
            case DEVICE_LOST:
                Log.d(TAG, "lost device");
                callbacks.onDeviceLost();
                break;
            case SPACE_ABANDONED:
                //opuszczamy nasz region schowaj menu
                Log.d(TAG, "namespace or region abandoned");
                callbacks.onSpaceAbandoned();
                break;
        }
    }

    public void start() {
        proximityManager.initializeScan(getScanContext(), new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.attachListener(Beacon.this);
            }

            @Override
            public void onConnectionFailure() {
            }
        });
    }

    public void stop() {
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
}
