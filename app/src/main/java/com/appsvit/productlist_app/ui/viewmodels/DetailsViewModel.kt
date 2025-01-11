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
class DetailsViewModel  @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _saveProductState = MutableLiveData<Response<Boolean>>(Response.None)
    val saveProductState: LiveData<Response<Boolean>> get() = _saveProductState

    private val _deleteProductState = MutableLiveData<Response<Boolean>>(Response.None)
    val deleteProductState: LiveData<Response<Boolean>> get() = _deleteProductState



    fun saveProduct(likedProduct: LikedProduct) {
        viewModelScope.launch {
            productRepository.saveProduct(likedProduct).collect {
                _saveProductState.value = it
            }
        }
    }

   fun deleteSavedProduct(likedProduct: LikedProduct) {
        viewModelScope.launch {
            productRepository.deleteSavedProduct(likedProduct).collect {
                _deleteProductState.value = it
            }
        }
    }
}