package hacksilesia.europejskiednidziedzictwa.mapsparser;

import hacksilesia.europejskiednidziedzictwa.mapsparser.MapLocation;


public class MapRiddle extends MapLocation {

    String question;
    String[] answer = {"", "", ""};
    int correctAns;

    public MapRiddle(String n, int i){
        super(n, i);

    }
}

