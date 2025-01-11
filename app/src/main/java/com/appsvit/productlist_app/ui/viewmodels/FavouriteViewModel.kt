package com.appsvit.productlist_app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.repository.ProductRepository
import com.appsvit.productlist_app.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _getSavedProductListState =
        MutableLiveData<Response<List<LikedProduct>>>(Response.None)
    val getSavedProductListState: LiveData<Response<List<LikedProduct>>> get() = _getSavedProductListState


    init {
        getSavedProducts()
    }

    fun getSavedProducts() {
        viewModelScope.launch {
            productRepository.getSavedProducts().collect {
                _getSavedProductListState.value = it
            }
        }
    }

}