public class MainActivity extends AppCompatActivity implements ProximityManager.ProximityListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProximityManagerContract proximityManager;
    private ScanContext scanContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KontaktSDK.initialize("api-key");
        proximityManager = new KontaktProximityManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        proximityManager.initializeScan(getScanContext(), new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.attachListener(MainActivity.this);
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
                break;
            case DEVICE_LOST:
                Log.d(TAG, "lost device");
                break;
            case SPACE_ABANDONED:
                //opuszczamy nasz region schowaj menu
                Log.d(TAG, "namespace or region abandoned");
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
    
    private ScanContext getScanContext() {
    if (scanContext == null) {
        scanContext = new ScanContext.Builder()
                //.setScanPeriod(ScanPeriod.RANGING) // or for monitoring for 15 seconds scan and 10 seconds waiting:
                .setScanPeriod(new ScanPeriod(TimeUnit.SECONDS.toMillis(15), TimeUnit.SECONDS.toMillis(10)))
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