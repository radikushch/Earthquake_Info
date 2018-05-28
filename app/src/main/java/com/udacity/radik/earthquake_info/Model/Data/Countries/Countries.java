package com.udacity.radik.earthquake_info.Model.Data.Countries;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Countries {

    @SerializedName("geonames")
    private List<GeoNames> geonames;
}
