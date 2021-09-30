package com.gokhanyilmaz.issdetector.ui.main;

import android.util.Log;

import com.gokhanyilmaz.issdetector.data.ResponseISSLocation;
import com.gokhanyilmaz.issdetector.service.ClientISS;
import com.gokhanyilmaz.issdetector.service.IRequest;
import com.gokhanyilmaz.issdetector.util.Constant;
import com.huawei.hms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private IRequest request;
    private boolean isChecked = true;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.request = ClientISS.getApiClient().create(IRequest.class);
    }

    @Override
    public void mapReady() {
        this.getISSLocation();
        view.initMarkerTrackListener();
    }

    @Override
    public void trackMarkerChanged(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public void getISSLocation() {
        Call<ResponseISSLocation> call = request.getISSLocation();
        call.enqueue(new Callback<ResponseISSLocation>() {
            @Override
            public void onResponse(Call<ResponseISSLocation> call, Response<ResponseISSLocation> response) {
                if (response.isSuccessful()) {
                    LatLng currentLatLng = response.body().getIssPosition().getLocationAsLatLng();
                    Log.w(Constant.TAG, "getISSLocation : " + currentLatLng.toString());
                    view.setMarker(currentLatLng);
                    view.drawRoute(currentLatLng);
                    if (isChecked)
                        view.moveCamera(currentLatLng);
                    waitAndCallRequest();
                }
            }

            @Override
            public void onFailure(Call<ResponseISSLocation> call, Throwable t) {
                Log.w(Constant.TAG, "getISSLocation - onFailure : " + t.getMessage());
                view.showMessage(t.getMessage());
            }
        });
    }

    /**
     * For get the ISS location info every 2 sec.
     */
    @Override
    public void waitAndCallRequest() {
        Log.d(Constant.TAG, "waitAndCallRequest ");
        new android.os.Handler().postDelayed(() -> getISSLocation(), 2000
        );
    }
}
