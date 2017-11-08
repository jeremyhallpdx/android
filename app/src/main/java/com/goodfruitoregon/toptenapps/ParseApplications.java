package com.goodfruitoregon.toptenapps;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 10/31/17.
 */

public class ParseApplications {

    private static final String TAG = "ParseApplications";

    private List<FeedEntry> applications;

    public ParseApplications() {
        this.applications = new ArrayList<>();
    }

    public List<FeedEntry> getApplications() {
        return applications;
    }

    public boolean parse(String xmlData) {

        boolean status = true; // gets set to false if an exception is thrown
        FeedEntry currentRecord = null; // holds each object to add to the list
        boolean inEntry = false; // set to true when we are inside an "entry" tag
        String textValue = ""; // stores the value of the current xml tag

        try {  // parse the xml data yo

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  // create a factory to handle the class
            factory.setNamespaceAware(true);  // sets the factory to create parsers that support xml namespaces
            XmlPullParser xpp = factory.newPullParser(); // uses the factory to create an instance of the parser
            xpp.setInput(new StringReader(xmlData));  // gives the parser the xml string source

            int eventType = xpp.getEventType(); // gets the current state of the parser
                                                // correlates to the events handled by the parser class

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = xpp.getName(); // gets the name of each tag

                switch (eventType) {  // switch to check the evenType

                    case XmlPullParser.START_TAG:  // if it's a Start Tag...

                        //Log.d(TAG, "parse: Starting tag for: " + tagName);

                        if("entry".equalsIgnoreCase(tagName)) { // if we get an entry tag

                            inEntry = true;  // flag we are in an entry_tag event
                            currentRecord = new FeedEntry();  // create a new record to store the data
                        }

                        break; // go to next parsing event

                    case XmlPullParser.TEXT:  // if we get a text event...

                        textValue = xpp.getText();  // store the text
                        break;  // go to next parsing event

                    case XmlPullParser.END_TAG: // if we get an ending_tag event...

                        //Log.d(TAG, "parse: Ending tag for: " + tagName);

                        if (inEntry) { // if we're in an entry tag then we can now save the record

                            if ("entry".equalsIgnoreCase(tagName)) { // we we are closing the entry tag

                                applications.add(currentRecord);  // store the record as we've gathered all the needed
                                inEntry = false;  // set to false so we can find the next entry start_tag
                            }

                            else if ("name".equalsIgnoreCase(tagName)) {  // if we are closing a name tag

                                currentRecord.setName(textValue);  // store the name value
                            }

                            else if ("artist".equalsIgnoreCase(tagName)) {  // if we are closing an artist tag

                                currentRecord.setArtist(textValue);  // store the artist value
                            }

                            else if ("releaseDate".equalsIgnoreCase(tagName)) {  // if we are closing a releaseDate tag

                                currentRecord.setReleaseDate(textValue);  // store the releaseDate value
                            }

                            else if ("summary".equalsIgnoreCase(tagName)) {  // if we are closing a summary tag

                                currentRecord.setSummary(textValue);  // store the summary value
                            }

                            else if ("image".equalsIgnoreCase(tagName)) {  // if we are closing an image tag

                                currentRecord.setImageURL(textValue);  // store the imageURL value
                            }
                        }
                        break;  // go to next parsing event

                    default:
                        // Nothing else to do.
                }

                eventType = xpp.next();
            }

//            for (FeedEntry f : applications) {
//
//                Log.d(TAG, "********************");
//                Log.d(TAG, f.toString());
//            }

        }

        catch (Exception e) { // catches all exceptions and prints the stack trace to the log/console

            status = false; // sets status to false indicating an error parsing the data
            e.printStackTrace();  // prints the stack trace to the log/console
        }

        return status;  // always true unless an exception is thrown
    }
}
