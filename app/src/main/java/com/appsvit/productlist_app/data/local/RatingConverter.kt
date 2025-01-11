package com.appsvit.productlist_app.data.local

import androidx.room.TypeConverter
import com.appsvit.productlist_app.data.models.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingConverter {
    @TypeConverter
    fun fromRating(rating: Rating): String {
        return Gson().toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingString: String): Rating {
        val type = object : TypeToken<Rating>() {}.type
        return Gson().fromJson(ratingString, type)
    }
}
