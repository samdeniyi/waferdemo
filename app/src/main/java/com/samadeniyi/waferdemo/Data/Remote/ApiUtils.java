package com.samadeniyi.waferdemo.Data.Remote;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://restcountries.eu/rest/v2/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
