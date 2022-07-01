package com.feed.services;


import com.feed.entity.FilterEntityParser;
import com.feed.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCall {

    @GET(Constants.GET_FILTERS)
    Call<FilterEntityParser> getFilters();
}
