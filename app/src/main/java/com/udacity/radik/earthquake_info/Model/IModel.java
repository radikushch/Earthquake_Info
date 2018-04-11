package com.udacity.radik.earthquake_info.Model;

import android.app.LoaderManager;

public interface IModel extends LoaderManager.LoaderCallbacks<String> {
    String getEarthquakes(String url);
}
