package com.appsvit.productlist_app.data.repository

import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.utils.Response
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductList(): Flow<Response<List<ProductItem>>>
    fun fetchDataFromDb(): Flow<Response<List<ProductItem>>>

    fun saveProduct(likedProduct: LikedProduct): Flow<Response<Boolean>>
    fun getSavedProducts(): Flow<Response<List<LikedProduct>>>
    fun deleteSavedProduct(likedProduct: LikedProduct): Flow<Response<Boolean>>
}

