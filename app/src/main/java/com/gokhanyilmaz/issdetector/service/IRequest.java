package com.gokhanyilmaz.issdetector.service;

import com.gokhanyilmaz.issdetector.data.ResponseISSLocation;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRequest {

    @GET("iss-now.json")
    Call<ResponseISSLocation> getISSLocation();
}
