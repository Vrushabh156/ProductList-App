package com.appsvit.productlist_app.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appsvit.productlist_app.utils.Constants.LIKED_ENTITY
import kotlinx.parcelize.Parcelize

//Another table to differentiate between liked products and the cached products

@Parcelize
@Entity(tableName = LIKED_ENTITY)
data class LikedProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String? = "",
    val description: String? = "",
    val image: String? = "",
    val price: Double? = 0.0,
    val rating: Rating? = Rating(),
    val title: String? = "",
    var isLiked: Boolean? = false,
) : Parcelable
