package com.goodfruitoregon.toptenapps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 11/1/17.
 */

public class FeedAdapter extends ArrayAdapter {

    private List<FeedEntry> applications;

    public FeedAdapter(@NonNull Context context, int resource) {

        super(context, resource);
        this.applications = new ArrayList<>();
    }
}
