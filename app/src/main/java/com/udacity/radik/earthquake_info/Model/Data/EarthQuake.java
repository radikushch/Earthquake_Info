package com.udacity.radik.earthquake_info.Model.Data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EarthQuake {
    private double mag;
    private String place;
    private Long time;
    private String url;
}
