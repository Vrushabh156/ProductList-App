package com.appsvit.productlist_app.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appsvit.productlist_app.utils.Constants.PRODUCT_ENTITY
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = PRODUCT_ENTITY)
data class ProductItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String? = "",
    val description: String? = "",
    val image: String? = "",
    val price: Double? = 0.0,
    val rating: Rating? = Rating(),
    val title: String? = "",
) : Parcelable
