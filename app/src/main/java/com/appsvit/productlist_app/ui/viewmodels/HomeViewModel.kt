package com.appsvit.productlist_app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.data.repository.ProductRepository
import com.appsvit.productlist_app.utils.Constants
import com.appsvit.productlist_app.utils.PreferenceManager
import com.appsvit.productlist_app.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productState = MutableLiveData<Response<List<ProductItem>>>(Response.None)
    val productState: LiveData<Response<List<ProductItem>>> get() = _productState

    private val _productFromDb = MutableLiveData<Response<List<ProductItem>>>(Response.None)
    val productFromDb: LiveData<Response<List<ProductItem>>> get() = _productFromDb


    init {
        getProductList()
    }

    fun userOnboarded() {
        viewModelScope.launch {
            preferenceManager.saveBooleanValue(Constants.FIRST, true)
        }
    }

    fun getProductList() {
        viewModelScope.launch {
            productRepository.getProductList().collect {
                _productState.value = it
            }
        }
    }


    fun fetchDataFromDb() {
        viewModelScope.launch {
            productRepository.fetchDataFromDb().collect {
                _productFromDb.value = it
            }
        }
    }
}