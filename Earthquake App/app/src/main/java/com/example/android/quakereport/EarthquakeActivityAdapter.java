package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeActivityAdapter<E> extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItemView =  convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);
        }
        Earthquake  current = getItem(position);
        TextView magView =(TextView)listItemView.findViewById(R.id.magnitude);
        String formattedMagnitude = formatMagnitude(current.getMagnitude());

        // Display the magnitude of the current earthquake in that TextView
        magView.setText(formattedMagnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(current.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        String originalLocation = current.getLocation();
        String primaryLocation;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        TextView primaryLocationView =(TextView)listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);
        TextView locationOffsetView =(TextView)listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        Date dateObject = new Date(current.getTimeInMiliSeconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
            int magColorId;
            int magFloor = (int)Math.floor(magnitude);
            switch (magFloor){
                case 0:
                case 1:
                    magColorId = R.color.magnitude1;
                    break;
                case 2:
                    magColorId = R.color.magnitude2;
                    break;
                case 3:
                    magColorId = R.color.magnitude3;
                    break;
                case 4:
                    magColorId = R.color.magnitude4;
                    break;
                case 5:
                    magColorId = R.color.magnitude5;
                    break;
                case 6:
                    magColorId = R.color.magnitude6;
                    break;
                case 7:
                    magColorId = R.color.magnitude7;
                    break;
                case 8:
                    magColorId = R.color.magnitude8;
                    break;
                case 9:
                    magColorId = R.color.magnitude9;
                    break;
                default:
                    magColorId = R.color.magnitude10plus;
                    break;
            }
            return ContextCompat.getColor(getContext(), magColorId);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    public EarthquakeActivityAdapter(Context context, List<Earthquake> earthquakes) {

        super(context,0,earthquakes);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
