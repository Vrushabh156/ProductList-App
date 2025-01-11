package com.appsvit.productlist_app.utils

import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem

interface NewsItemClicksListener {
    fun onItemClicked(product: ProductItem, likedProduct: LikedProduct)
}