package com.udacity.radik.earthquake_info;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;
import com.udacity.radik.earthquake_info.Model.Database.AppDatabase;

public class EarthQuakeApplication extends Application {

    public static EarthQuakeApplication instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static EarthQuakeApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
