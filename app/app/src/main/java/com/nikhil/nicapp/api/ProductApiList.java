package com.nikhil.nicapp.api;

import com.nikhil.nicapp.model.Product;
import com.nikhil.nicapp.model.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApiList {
    @GET("products")
    Call<ProductResponse> getProducts();
}
