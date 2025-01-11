package com.appsvit.productlist_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem


@Database(entities = [ProductItem::class, LikedProduct::class], version = 1)
@TypeConverters(RatingConverter::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}