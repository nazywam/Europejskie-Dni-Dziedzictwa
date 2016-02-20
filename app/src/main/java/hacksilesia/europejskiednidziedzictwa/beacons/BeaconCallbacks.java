package hacksilesia.europejskiednidziedzictwa.beacons;

import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;

import java.util.List;

/**
 * Created by angela on 2/20/16.
 */
public interface BeaconCallbacks {
    void onDeviceDiscovered(List<? extends RemoteBluetoothDevice> lista);
}
