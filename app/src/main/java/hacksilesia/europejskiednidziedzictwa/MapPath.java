package hacksilesia.europejskiednidziedzictwa;

import java.util.ArrayList;

public class MapPath {
    //MapLocation from
    //MapLocation to

    int index;
    ArrayList<MapPoint> coordinates;

    public MapPath(int i){
        index  = i;
        coordinates = new ArrayList<MapPoint>();
    }
}
