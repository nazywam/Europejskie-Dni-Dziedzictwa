package hacksilesia.europejskiednidziedzictwa.mapsparser;

public class MapLocation extends MapPoint {
    int index;
    public String description;
    public String name;

    public MapLocation(String n, int i){
        super(0, 0);
        index = i;
        name = n;
        description = ":)";
    }
}
