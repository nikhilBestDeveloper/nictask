package com.nikhil.nicapp.model;

import java.util.Collections;
import java.util.List;

public class ProductResponse {
    List<Product> products = Collections.emptyList();

    public List<Product> get() {
        return products;
    }
}
