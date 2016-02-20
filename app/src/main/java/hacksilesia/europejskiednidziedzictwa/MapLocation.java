package hacksilesia.europejskiednidziedzictwa;

public class MapLocation {
    MapPoint mapPoint;
    int index;
    String description;

    public MapLocation(float lat, float lon, int i){
        mapPoint = new MapPoint(lat, lon);
        index = i;
    }
}
