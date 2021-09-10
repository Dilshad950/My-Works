/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL1 = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
   private static final  String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private static final String USGS_REQUEST_URL2 = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake";
    private EarthquakeActivityAdapter myAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView emptyTextView ;
    View loadingIndicator;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        myAdapter = new EarthquakeActivityAdapter<>(
                this, new ArrayList<Earthquake>());

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView = findViewById(R.id.empty_view);
           emptyTextView.setText(R.string.no_internet_connection);

            // Update empty state with no connection error message
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                emptyTextView = findViewById(R.id.empty_view);
                emptyTextView.setText("");
                getEarthquakeData();
            }
        });

        getEarthquakeData();

        // Find a reference to the {@link ListView} in the layout
        final ListView earthquakeListView = (ListView) findViewById(R.id.list);
        emptyTextView =findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(emptyTextView)  ;
        // Create a new {@link ArrayAdapter} of earthquakes
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(myAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake current = (Earthquake) myAdapter.getItem(i);
                Uri earthquakeUri = Uri.parse(current.getUrl());
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(siteIntent);
            }
        });
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        Uri baseUri = Uri.parse(USGS_REQUEST_URL2);
        Uri.Builder uriBuilder = baseUri.buildUpon();

//        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("orderby", orderBy);
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
//        uriBuilder.appendQueryParameter("limit", "10");


        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);
        myAdapter.clear();
        Toast.makeText(EarthquakeActivity.this, "Loading Finished", Toast.LENGTH_SHORT).show();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            myAdapter.addAll(earthquakes);
        }
        emptyTextView.setText(R.string.no_earthqaukes);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        myAdapter.clear();
    }
    private void getEarthquakeData(){
        loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.VISIBLE);
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            myAdapter.clear();
            // First, hide loading indicator so error message will be visible
            loadingIndicator = findViewById(R.id.progress_bar);
           loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            emptyTextView.setText(R.string.no_internet_connection);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        if(mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
