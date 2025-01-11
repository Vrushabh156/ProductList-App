package com.appsvit.productlist_app.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsvit.productlist_app.R
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.data.models.Rating
import com.appsvit.productlist_app.ui.adapters.ProductListAdapter
import com.appsvit.productlist_app.utils.Constants
import com.appsvit.productlist_app.utils.NetworkUtil
import com.appsvit.productlist_app.utils.Response
import com.appsvit.productlist_app.utils.Status
import com.appsvit.productlist_app.utils.snackBarUtil
import com.appsvit.productlist_app.databinding.FragmentHomeBinding
import com.appsvit.productlist_app.databinding.ProductItemBinding
import com.appsvit.productlist_app.databinding.SaveProductItemBinding
import com.appsvit.productlist_app.ui.viewmodels.HomeViewModel
import com.appsvit.productlist_app.utils.NewsItemClicksListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), NewsItemClicksListener {

    @Inject
    lateinit var networkUtil: NetworkUtil

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private lateinit var context: Context
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var productListAdapter: ProductListAdapter
    private var networkAvailable: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.VISIBLE

        homeViewModel.userOnboarded()

        networkUtil.observe(viewLifecycleOwner) {
            when (it) {
                Status.AVAILABLE -> {
                    networkAvailable = true
                    homeViewModel.getProductList()
                }

                Status.UNAVAILABLE -> {
                    networkAvailable = false
                    snackBarUtil(binding.root, Constants.UNAVAILABLE)
                    homeViewModel.fetchDataFromDb()
                }

                Status.LOSING -> {
                    networkAvailable = true
                    snackBarUtil(binding.root, Constants.LOSING)
                }
            }
        }

        homeViewModel.productState.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Error -> {
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                    if (!networkAvailable) {
                        // Already handled in Status.UNAVAILABLE
                        return@observe
                    }
                    snackBarUtil(binding.root, it.message.toString())
                }

                Response.Loading -> {
                    binding.mainLayout.visibility = View.GONE
                    binding.loadingLayout.visibility = View.VISIBLE
                }

                Response.None -> {
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                }

                is Response.Success -> {
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                    if (it.data != null) {
                        setHomeData(it.data)
                    }
                }
            }
        }

        homeViewModel.productFromDb.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Error -> {
                    snackBarUtil(binding.root, it.message.toString())
                }

                Response.Loading -> {
                    binding.mainLayout.visibility = View.GONE
                    binding.loadingLayout.visibility = View.VISIBLE
                }

                Response.None -> {
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                }

                is Response.Success -> {
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                    Log.d("articles", it.data.toString())
                    setHomeData(it.data!!)
                }
            }
        }

        return binding.root
    }

    private fun setHomeData(list: List<ProductItem>) {
        productListAdapter = ProductListAdapter(list, this)
        binding.recyclerView.adapter = productListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(product: ProductItem, likedProduct: LikedProduct) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(product, likedProduct)
        findNavController().navigate(action)

    }

}