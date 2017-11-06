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

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = layoutInflater.inflate(layoutResource, parent, false);  // create a view inflated by the inflater
                                                                                            // pass the layout res file
                                                                                            // inflates the "constraint layout" which contains the textView widgets

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        FeedEntry currentApp = applications.get(position);  // retrieves a record from the data source

        viewHolder.tvName.setText(currentApp.getName());  // sets the text in the views
        viewHolder.tvArtist.setText(currentApp.getArtist());
        viewHolder.tvSummary.setText(currentApp.getSummary());

        return convertView;  // returns the viewGroup to the listView to display the data
    }

    private class ViewHolder {

        final TextView tvName;
        final TextView tvArtist;
        final TextView tvSummary;

        ViewHolder(View v) {

            this.tvName = v.findViewById(R.id.tvName);
            this.tvArtist = v.findViewById(R.id.tvArtist);
            this.tvSummary = v.findViewById(R.id.tvSummary);
        }
    }
}
