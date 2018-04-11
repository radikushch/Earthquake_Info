package com.udacity.radik.earthquake_info;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

public class JSONLoader extends AsyncTaskLoader<String> {

    public JSONLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONString();
    }
}
