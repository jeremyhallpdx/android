package com.goodfruitoregon.toptenapps;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listApps = findViewById(R.id.xmllListView);

        Log.d(TAG, "onCreate: Starting AsyncTask");
        DownloadData dlData = new DownloadData();
        dlData.execute(getResources().getString(R.string.top_ten_rss_url));
        Log.d(TAG, "onCreate: Done");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);

            ParseApplications parser = new ParseApplications();
            parser.parse(s);

            FeedAdapter adapter = new FeedAdapter(MainActivity.this, R.layout.list_record, parser.getApplications());
            listApps.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... strings) {

            Log.d(TAG, "doInBackground: Starts with: " + strings[0]);

            String rssFeed = downloadXML(strings[0]);

            if (rssFeed == null) {

                Log.e(TAG, "doInBackground: Error downloading");
            }

            return rssFeed;
        }

        private String downloadXML(String urlPath) {

            StringBuilder xmlResult = new StringBuilder();

            try {

                URL url = new URL(urlPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                int response = conn.getResponseCode();
                Log.d(TAG, "downloadXML: Response code: " + response);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                int charsRead; // counts the number of chars read from the stream
                char[] inputBuffer = new char[500]; // holds the chars read (up to 500)

                while (true) {  // infinite loop to read the input stream.  Breaks when eof is reached.

                    // returns an integer for the number of chars read.  Returns -1 if end of stream has been reached.
                    charsRead = bufferedReader.read(inputBuffer);

                    if (charsRead < 0) { // if the .read() return is -1 (eof reached)

                        break;
                    }

                    if (charsRead > 0) { // if the BufferedReader reads data...

                        // overloaded method to append the inputBuffer array into the StringBuilder
                        // 1st arg is the sub-array to append to append into.
                        // 2nd is the starting element of subarray.
                        // 3rd is number of chars to append.
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }

                bufferedReader.close(); // closes the connection and all resources associated with it

                return xmlResult.toString();  // returns the xml data for parsing.
            }

            catch (MalformedURLException e) {

                Log.e(TAG, "downloadXML: Invalid URL" + e.getMessage());
            }

            catch (IOException e) {

                Log.e(TAG, "downloadXML: Error Reading Data: " + e.getMessage());
            }

            catch (SecurityException e) {

                Log.e(TAG, "downloadXML: Security Exception. Needs permissions. " + e.getMessage());
            }

            return null;  // return null if exceptions are caught.
        }
    }
}
