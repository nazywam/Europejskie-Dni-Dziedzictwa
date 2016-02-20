package hacksilesia.europejskiednidziedzictwa.beacons;

/**
 * Created by angela on 2/20/16.
 */
public interface BeaconCallbacks {
    void onBeaconUpdate();
    void onSpaceAbandoned();
    void onDeviceLost();
    void onDeviceDiscovered();
    void onSpaceEntered();
}
