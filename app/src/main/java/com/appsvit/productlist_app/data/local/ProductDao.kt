package com.appsvit.productlist_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<ProductItem>)

    @Query("SELECT * FROM `products`")
    suspend fun getAllProduct(): List<ProductItem>

//    @Delete
//    suspend fun deleteArticle(article: ProductItem)

    @Query("DELETE FROM `products`")
    suspend fun deleteAllProduct()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(likedProduct: LikedProduct)

    @Query("SELECT * FROM `liked`")
    suspend fun getSavedProducts(): List<LikedProduct>

    @Delete
    suspend fun deleteSavedProduct(likedProduct: LikedProduct)

}