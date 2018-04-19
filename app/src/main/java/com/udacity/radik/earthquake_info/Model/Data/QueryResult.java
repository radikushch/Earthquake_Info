package com.udacity.radik.earthquake_info.Model.Data;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by Radik on 31.03.2018.
 */

@Data
@NoArgsConstructor
public class QueryResult {
    @SerializedName("features")
    private List<Features> features;
}

