package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String myUrl;
    public EarthquakeLoader(Context context , String myUrl) {
        super(context);
        this.myUrl =myUrl;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (myUrl == null) {
            return null;
        }
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(myUrl);
        return earthquakes;
    }
}
