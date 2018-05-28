package com.udacity.radik.earthquake_info.Model.Data.Countries;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class GeoNames {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @SerializedName("countryName")
    private String countryName;
    @SerializedName("west")
    private double west;
    @SerializedName("east")
    private double east;
    @SerializedName("north")
    private double north;
    @SerializedName("south")
    private double south;
}
