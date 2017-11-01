package com.goodfruitoregon.toptenapps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeremy on 11/1/17.
 */

public class FeedAdapter extends ArrayAdapter {

    private static final String TAG = "FeedAdapter";
    private final int layoutResource;  // layoutResource file.  ViewGroup to display in listView widget
    private final LayoutInflater layoutInflater;  // the actual LayoutInflater obj.  get this from Context.
    private List<FeedEntry> applications;  // the data source that the listView will display data from

    public FeedAdapter(@NonNull Context context, int resource, List<FeedEntry> applications) {

        super(context, resource);
        this.layoutResource = resource;  // the resource file passed when calling the constructor
        this.layoutInflater = LayoutInflater.from(context);  // getting the inflater from the context
        this.applications = applications;  // initializing the data source
    }

    @Override
    public int getCount() {
        return applications.size();  // returns the number of elements to list.  needed for the listview to display data
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(layoutResource, parent, false);  // create a view inflated by the inflater
                                                                                        // pass the layout res file
                                                                                        // inflates the "constraint layout" which contains the textView widgets
        TextView tvName = view.findViewById(R.id.tvName);  // instantiate the views to be passed over to the listView
        TextView tvArtist = view.findViewById(R.id.tvArtist);
        TextView tvSummary = view.findViewById(R.id.tvSummary);
        FeedEntry currentApp = applications.get(position);  // retrieves a record from the data source

        tvName.setText(currentApp.getName());  // sets the text in the views
        tvArtist.setText(currentApp.getArtist());
        tvSummary.setText(currentApp.getSummary());

        return view;  // returns the viewGroup to the listView to display the data
    }
}
