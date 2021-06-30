package com.gokhanyilmaz.issdetector.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huawei.hms.maps.model.LatLng;

public class ISSPosition {

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    /**
     * For get the location info as LatLng format
     */
    public LatLng getLocationAsLatLng() {
        return new LatLng(Double.parseDouble(this.latitude), Double.parseDouble(this.longitude));
    }
}
