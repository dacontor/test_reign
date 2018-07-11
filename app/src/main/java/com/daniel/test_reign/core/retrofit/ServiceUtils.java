package com.daniel.test_reign.core.retrofit;

import com.daniel.test_reign.core.retrofit.methods.ApiMethods;

public class ServiceUtils {

    private static String baseURL = "https://hn.algolia.com/api/v1/";

    private ServiceUtils() {
    }

    public static ApiMethods getAPIService() {

        return ServiceCore.getClient(baseURL).create(ApiMethods.class);

    }

}
