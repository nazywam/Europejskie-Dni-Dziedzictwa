package hacksilesia.europejskiednidziedzictwa.mapsparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class UserHandler extends DefaultHandler {

    String folderType = "";

    String folderTag = "close";
    String nameTag = "close";
    String placeMarkerTag = "close";
    String coordinatesTag = "close";

    public ArrayList<MapLocation> mapLocations = new ArrayList<MapLocation>();
    public ArrayList<MapPath> mapPaths = new ArrayList<MapPath>();
    public ArrayList<MapRiddle> mapRiddles = new ArrayList<MapRiddle>();

    int locationIterator = 0;
    int pathsIterator = 0;
    int riddleIterator = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
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
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (nameTag.equalsIgnoreCase("open") && folderTag == "open"){
            if(placeMarkerTag == "close"){
                folderType = new String(ch, start, length);
                //System.out.print("FolderName = "+ folderType + "\n");
            } else {
                String nodeName = new String(ch, start, length);
                if(folderType.equalsIgnoreCase("points")){


                    System.out.print("New Point with name: " + nodeName + "\n");
                } else if(folderType.equalsIgnoreCase("path")){
                    System.out.print("New Path segment: " + nodeName + "\n");

                    MapPath p = new MapPath(pathsIterator);
                    mapPaths.add(p);
                    pathsIterator++;
                }
            }
        }
        if(coordinatesTag == "open"){
            System.out.print("Coords: " + new String(ch, start, length) + "\n");

            String[] loc = new String(ch, start, length).split(",");

            if(folderType.equalsIgnoreCase("points")){
                MapLocation l = new MapLocation(Float.parseFloat(loc[0]), Float.parseFloat(loc[1]), locationIterator);
                mapLocations.add(l);
                locationIterator++;
            }
            if(folderType.equalsIgnoreCase("path")){
                for(int i=0; i<loc.length/3; i++) {
                    MapPoint p = new MapPoint(Float.parseFloat(loc[i*3]), Float.parseFloat(loc[i*3+1]));
                    mapPaths.get(mapPaths.size() - 1).coordinates.add(p);
                }
            }
            if(folderType.equalsIgnoreCase("riddle")){
                MapRiddle r = new MapRiddle(Float.parseFloat(loc[0]), Float.parseFloat(loc[1]), riddleIterator);
                riddleIterator++;
                mapRiddles.add(r);
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
    }
}