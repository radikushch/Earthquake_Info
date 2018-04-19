package com.udacity.radik.earthquake_info.Model.Data;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Features {
    private String type;
    @SerializedName("properties")
    private EarthQuake properties;
}
