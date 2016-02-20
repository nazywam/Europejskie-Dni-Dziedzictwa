package hacksilesia.europejskiednidziedzictwa.mapsparser;

public class MapLocation extends MapPoint {
    int index;
    String description;

    public MapLocation(float lat, float lon, int i){
        super(lat, lon);
        index = i;
    }
}
