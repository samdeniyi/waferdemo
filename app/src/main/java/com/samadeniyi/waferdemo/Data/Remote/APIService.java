package com.samadeniyi.waferdemo.Data.Remote;
import com.samadeniyi.waferdemo.Data.Model.EuCountries;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {
    @GET("all")
    Call<List<EuCountries>> getEuCountries();
}
