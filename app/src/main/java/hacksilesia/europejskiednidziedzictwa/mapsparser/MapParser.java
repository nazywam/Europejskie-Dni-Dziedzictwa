package hacksilesia.europejskiednidziedzictwa.mapsparser;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MapParser {
    private UserHandler userhandler;

    public MapParser(Activity ac){
        try {
            InputStream inputFile = ac.getAssets().open("Example.kml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserHandler getUserHandler() {
        return userhandler;
    }
}