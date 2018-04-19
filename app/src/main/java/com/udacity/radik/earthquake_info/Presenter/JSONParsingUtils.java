package com.udacity.radik.earthquake_info.Presenter;


import android.annotation.SuppressLint;

import com.udacity.radik.earthquake_info.Model.Data.EarthQuake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Radik on 31.03.2018.
 */

public final class JSONParsingUtils {

    private JSONParsingUtils() {

    }

    public static ArrayList<EarthQuake> parseEarthQuakeData(String JSONString) {
        ArrayList<EarthQuake> earthQuakes = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(JSONString);
            JSONArray features = root.optJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject properties = ((JSONObject)features.get(i)).optJSONObject("properties");
                EarthQuake earthQuake = new EarthQuake();
                setMagnitudeToEartQuake(properties.getDouble("mag"), earthQuake);
                setLocationToEartQuake(properties.getString("place"), earthQuake);
                setDateToEarhQuake(properties.getLong("time"), earthQuake);
               // earthQuake.setUrl(new URL(properties.getString("url")));
                earthQuakes.add(earthQuake);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return earthQuakes;
    }

    private static void setMagnitudeToEartQuake(double mag, EarthQuake earthQuake) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        earthQuake.setMag(Double.valueOf(decimalFormat.format(mag)));
    }

    private static void setLocationToEartQuake(String place, EarthQuake earthQuake) {
        String distance, location;
        if(place.contains("of")){
            distance = place.substring(0, place.indexOf("of") + 2);
            location = place.substring(place.indexOf("of") + 3, place.length());
        } else {
            distance = "Near the";
            location = place;
        }
        earthQuake.setPlace(distance);
        earthQuake.setPlace(location);
    }

    private static void setDateToEarhQuake(long time, EarthQuake earthQuake) {
        Date dateObject = new Date(time);
//        earthQuake.setTime(formatDate(dateObject));
//        earthQuake.setTime(formatTime(dateObject));
    }

    private static String formatTime(Date dateObject) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private static String formatDate(Date dateObject) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

}
