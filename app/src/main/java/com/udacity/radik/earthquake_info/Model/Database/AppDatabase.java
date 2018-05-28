package com.udacity.radik.earthquake_info.Model.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.udacity.radik.earthquake_info.Model.Data.Countries.GeoNames;

@Database(entities = {GeoNames.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GeoNamesDAO geoNamesDAO();
}
