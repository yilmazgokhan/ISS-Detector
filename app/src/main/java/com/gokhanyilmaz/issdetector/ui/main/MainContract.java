package com.gokhanyilmaz.issdetector.ui.main;

import android.os.Bundle;

import com.huawei.hms.maps.model.LatLng;

public interface MainContract {

    interface View {

        void initHuaweiMap(Bundle bundle);

        void initMarkerTrackListener();

        void setMarker(LatLng latLng);

        void drawRoute(LatLng latLng);

        void moveCamera(LatLng latLng);

        void showErrorMessage(String message);
    }

    interface Presenter {

        void mapReady();

        void trackMarkerChanged(Boolean isChecked);

        void getISSLocation();

        void waitAndCallRequest();
    }
}
