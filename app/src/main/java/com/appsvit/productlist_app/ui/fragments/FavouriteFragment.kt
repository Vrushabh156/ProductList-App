package com.appsvit.productlist_app.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsvit.productlist_app.R
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.databinding.FragmentFavouriteBinding
import com.appsvit.productlist_app.databinding.FragmentHomeBinding
import com.appsvit.productlist_app.databinding.SaveProductItemBinding
import com.appsvit.productlist_app.ui.adapters.ProductListAdapter
import com.appsvit.productlist_app.ui.adapters.SavedProductListAdapter
import com.appsvit.productlist_app.ui.viewmodels.FavouriteViewModel
import com.appsvit.productlist_app.utils.NewsItemClicksListener
import com.appsvit.productlist_app.utils.Response
import com.appsvit.productlist_app.utils.snackBarUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), NewsItemClicksListener {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding: FragmentFavouriteBinding get() = _binding!!
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var savedProductListAdapter: SavedProductListAdapter
    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.VISIBLE



        favouriteViewModel.getSavedProducts()

        favouriteViewModel.getSavedProductListState.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Error -> snackBarUtil(binding.root, it.message.toString())
                Response.Loading -> {}
                Response.None -> {}
                is Response.Success -> {
                    if (it.data != null) {
                        setHomeData(it.data)
                    }
                }
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setHomeData(list: List<LikedProduct>) {
        savedProductListAdapter = SavedProductListAdapter(list, this)
        binding.rvNews.adapter = savedProductListAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onItemClicked(product: ProductItem, likedProduct: LikedProduct) {
        val action =
            FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(
                product,
                likedProduct
            )
        findNavController().navigate(action)
    }

}