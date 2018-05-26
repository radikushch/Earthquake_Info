package com.udacity.radik.earthquake_info.Model.Data.EarthQuakes;

import com.google.gson.annotations.SerializedName;
import com.udacity.radik.earthquake_info.Model.Data.EarthQuakes.EarthQuake;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Features {
    private String type;
    @SerializedName("properties")
    private EarthQuake earthQuake;
}
