package hacksilesia.europejskiednidziedzictwa.mapsparser;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class UserHandler extends DefaultHandler {

    String folderType = "";
    String dataName = "";

    String folderTag = "close";
    String nameTag = "close";
    String placeMarkerTag = "close";
    String coordinatesTag = "close";
    String descriptionTag = "close";
    String dataTag = "close";
    String valueTag = "close";

    public ArrayList<MapLocation> mapLocations = new ArrayList<MapLocation>();
    public ArrayList<MapPath> mapPaths = new ArrayList<MapPath>();
    public ArrayList<MapRiddle> mapRiddles = new ArrayList<MapRiddle>();

    int locationIterator = 0;
    int pathsIterator = 0;
    int riddleIterator = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Log.d("PATHEEE", "start");
        if (qName.equalsIgnoreCase("folder")) {
            folderTag = "open";
        }
        if(qName.equalsIgnoreCase("name")){
            nameTag = "open";
        }
        if(qName.equalsIgnoreCase("placeMark")){
            placeMarkerTag = "open";
        }
        if(qName.equalsIgnoreCase("coordinates")){
            coordinatesTag = "open";
        }
        if(qName.equalsIgnoreCase("description")){
            descriptionTag= "open";
        }
        if(qName.equalsIgnoreCase("value")){
            valueTag= "open";
        }
        if(qName.equalsIgnoreCase("data")){
            dataTag= "open";
            dataName = attributes.getValue(0);
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (nameTag.equalsIgnoreCase("open") && folderTag == "open"){
            if(placeMarkerTag == "close"){
                folderType = new String(ch, start, length);
                Log.d("PATHEEE", "FolderName = "+ folderType + "\n");
            } else {
                String nodeName = new String(ch, start, length);
                if(folderType.equalsIgnoreCase("points")){
                    MapLocation l = new MapLocation(nodeName, locationIterator);
                    mapLocations.add(l);
                    locationIterator++;

                } else if(folderType.equalsIgnoreCase("path")) {
                    Log.d("PATHEEE", "New Path segment: " + nodeName + "\n");

                    MapPath p = new MapPath(pathsIterator);
                    mapPaths.add(p);
                    pathsIterator++;
                } else if(folderType.equalsIgnoreCase("riddle")){
                    MapRiddle r = new MapRiddle(nodeName, riddleIterator);
                    riddleIterator++;
                    mapRiddles.add(r);
                }
            }
        }
        if(valueTag == "open"){
            if(dataName.equalsIgnoreCase("descrtiption")){
                if(folderType.equalsIgnoreCase("points")){
                    mapLocations.get(mapLocations.size()-1).description = new String(ch, start, length);
                } else if(folderType.equalsIgnoreCase("riddle")){
                    mapRiddles.get(mapRiddles.size()-1).description = new String(ch, start, length);
                }
            } else if(dataName.equalsIgnoreCase("beaconId")){
                mapLocations.get(mapLocations.size()-1).beaconId = new String(ch, start, length);
            }
        }

        if(coordinatesTag == "open"){

            if(folderType.equalsIgnoreCase("points")){
                String[] loc = new String(ch, start, length).split(",");
                mapLocations.get(mapLocations.size()-1).latitude = Float.parseFloat(loc[0]);
                mapLocations.get(mapLocations.size()-1).longitude = Float.parseFloat(loc[1]);
            }
            if(folderType.equalsIgnoreCase("path")){
                String[] ps = new String(ch, start, length).split(" ");

                for(int i=0; i<ps.length; i++) {
                    String[] q = ps[i].split(",");
                    MapPoint p = new MapPoint(Float.parseFloat(q[0]), Float.parseFloat(q[1]));
                    mapPaths.get(mapPaths.size() - 1).coordinates.add(p);
                }
            }
            if(folderType.equalsIgnoreCase("riddle")){
                String[] loc = new String(ch, start, length).split(",");
                mapRiddles.get(mapRiddles.size()-1).latitude = Float.parseFloat(loc[0]);
                mapRiddles.get(mapRiddles.size()-1).longitude = Float.parseFloat(loc[1]);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("folder")) {
            folderTag = "close";
            folderType = "";
        }
        if(qName.equalsIgnoreCase("name")){
            nameTag = "close";
        }
        if(qName.equalsIgnoreCase("placeMark")){
            placeMarkerTag = "close";
        }
        if(qName.equalsIgnoreCase("coordinates")){
            coordinatesTag = "close";
        }
        if(qName.equalsIgnoreCase("description")){
            descriptionTag = "close";
        }
        if(qName.equalsIgnoreCase("data")){
            dataTag = "close";
        }
        if(qName.equalsIgnoreCase("value")){
            valueTag = "close";
        }
    }
}