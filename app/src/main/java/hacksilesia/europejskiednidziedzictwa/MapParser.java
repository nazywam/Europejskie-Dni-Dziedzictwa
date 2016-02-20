package hacksilesia.europejskiednidziedzictwa;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MapParser {
    public static void main(String[] args){
        try {
            File inputFile = new File("Example.kml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserHandler extends DefaultHandler {

    String folderType = "";

    String folderTag = "close";
    String nameTag = "close";
    String placeMarkerTag = "close";
    String coordinatesTag = "close";

    int pointIterator = 0;
    int pathIterator = 0;

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
                System.out.print("FolderName = "+ folderType + "\n");
            } else {
                String nodeName = new String(ch, start, length);
                if(folderType.equalsIgnoreCase("points")){
                    System.out.print("New Point with name: " + nodeName + "\n");
                } else if(folderType.equalsIgnoreCase("path")){
                    System.out.print("New Path segment: " + nodeName + "\n");
                }
            }
        }
        if(coordinatesTag == "open"){
            System.out.print("Coords: " + new String(ch, start, length) + "\n");
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