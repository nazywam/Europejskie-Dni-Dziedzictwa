package hacksilesia.europejskiednidziedzictwa.mapsparser;

import java.util.ArrayList;

public class MapPath {
    //MapLocation from
    //MapLocation to

    int index;
    public ArrayList<MapPoint> coordinates;

    public MapPath(int i){
        index  = i;
        coordinates = new ArrayList<MapPoint>();
    }
}
