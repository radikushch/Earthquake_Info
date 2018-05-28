package com.udacity.radik.earthquake_info.Model;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.udacity.radik.earthquake_info.Model.Data.Countries.GeoNames;

import java.util.List;

@Dao
public interface GeoNamesDAO {

    @Query("SELECT * FROM geonames")
    List<GeoNames> getAll();

    @Query("SELECT * FROM geonames WHERE id = :id")
    GeoNames getByID(long id);

    @Insert
    void insert (GeoNames geoNames);

    @Update
    void update(GeoNames geoNames);

    @Delete
    void delete(GeoNames geoNames);


}
