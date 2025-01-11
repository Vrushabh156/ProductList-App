package com.appsvit.productlist_app.data.repository.impl

import com.appsvit.productlist_app.data.local.ProductDatabase
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.data.remote.ProductService
import com.appsvit.productlist_app.data.repository.ProductRepository
import com.appsvit.productlist_app.utils.Constants
import com.appsvit.productlist_app.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductService,
    private val productDatabase: ProductDatabase
) : ProductRepository {

    override fun getProductList(): Flow<Response<List<ProductItem>>> = flow {
        try {
            emit(Response.None)
            emit(Response.Loading)
            val apiCall = productApi.getProductList()
            if (apiCall.isSuccessful) {
                val data = apiCall.body()
                emit(Response.Success(data)) //Emitting the data to the viewmodel
                productDatabase.productDao.deleteAllProduct() //Clear the database to avoid bulking
                productDatabase.productDao.insertProduct(
                    data?.subList(0, minOf(data.size, 10)) ?: emptyList()
                ) //Caching the articles

            } else {
                emit(Response.Success(null))
            }

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: Constants.ERR))

        }
    }


    override fun saveProduct(likedProduct: LikedProduct): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.None)
            emit(Response.Loading)
            productDatabase.productDao.saveProduct(likedProduct)
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: Constants.ERR))
        }
    }

    override fun getSavedProducts(): Flow<Response<List<LikedProduct>>> = flow {
        try {
            emit(Response.None)
            emit(Response.Loading)
            val likedProduct = productDatabase.productDao.getSavedProducts()
            emit(Response.Success(likedProduct))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: Constants.ERR))
        }
    }

    override fun deleteSavedProduct(likedProduct: LikedProduct): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.None)
            emit(Response.Loading)
            productDatabase.productDao.deleteSavedProduct(likedProduct)
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: Constants.ERR))
        }
    }

    override fun fetchDataFromDb(): Flow<Response<List<ProductItem>>> = flow {
        try {
            emit(Response.None)
            emit(Response.Loading)
            val data = productDatabase.productDao.getAllProduct()
            emit(Response.Success(data))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: Constants.ERR))
        }
    }
}
