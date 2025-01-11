package com.appsvit.productlist_app.data.remote

import com.appsvit.productlist_app.data.models.ProductItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("/products")
    suspend fun getProductList(): Response<List<ProductItem>>

}