package com.udacity.radik.earthquake_info.Model.Data.Countries;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GeoNames {

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
