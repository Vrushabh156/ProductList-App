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
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.appsvit.productlist_app.R
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.data.models.Rating
import com.appsvit.productlist_app.databinding.FragmentDetailsBinding
import com.appsvit.productlist_app.ui.viewmodels.DetailsViewModel
import com.appsvit.productlist_app.utils.Constants
import com.appsvit.productlist_app.utils.NetworkUtil
import com.appsvit.productlist_app.utils.Response
import com.appsvit.productlist_app.utils.Status
import com.appsvit.productlist_app.utils.snackBarUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    @Inject
    lateinit var networkUtil: NetworkUtil

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var context: Context
    private var networkAvailable: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.GONE

        // Observing network connectivity
        networkUtil.observe(viewLifecycleOwner) {
            when (it) {
                Status.AVAILABLE -> networkAvailable = true
                Status.UNAVAILABLE -> {
                    networkAvailable = false
                    snackBarUtil(binding.root, Constants.UNAVAILABLE)
                }

                Status.LOSING -> {
                    networkAvailable = true
                    snackBarUtil(binding.root, Constants.LOSING)
                }
            }
        }

        detailsViewModel.saveProductState.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Error -> snackBarUtil(binding.root, it.message.toString())
                Response.Loading -> {}
                Response.None -> {}
                is Response.Success -> snackBarUtil(binding.root, "Added to favourites!")
            }
        }

        detailsViewModel.deleteProductState.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Error -> snackBarUtil(binding.root, it.message.toString())
                Response.Loading -> {}
                Response.None -> {}
                is Response.Success -> {
                    snackBarUtil(binding.root, "Removed from favourites!")
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().popBackStack(R.id.favouriteFragment, false)
                    }, 1000)
                }
            }
        }

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        val productItem = args.ProductItem
        val likedProduct = args.LikedProduct

        if (productItem.title != ProductItem().title) {
            // Display product details
            binding.productItemTitleTv.text = productItem.title
            binding.productItemAmtTv.text = productItem.price.toString()
            binding.productItemIv.load(productItem.image) {
                crossfade(true)
                placeholder(R.drawable.exclamation)
                transformations(RoundedCornersTransformation())
            }
            binding.saveBtn.visibility = View.VISIBLE
            binding.ivDelete.visibility = View.GONE
            binding.saveBtn.isChecked = likedProduct.isLiked == true

            // Check if the product is already liked
            if (likedProduct.isLiked == true) {
                binding.saveBtn.setButtonDrawable(R.drawable.heart_red)
            } else {
                binding.saveBtn.setButtonDrawable(R.drawable.heart)
            }

            binding.saveBtn.setOnClickListener {

                if (likedProduct.isLiked == true) {
                    snackBarUtil(binding.root, "Already saved to favourites")
                } else {
                    val newLikedProduct = LikedProduct(
                        category = productItem.category ?: "",
                        description = productItem.description ?: "",
                        image = productItem.image ?: "",
                        price = productItem.price ?: 0.0,
                        rating = productItem.rating ?: Rating(),
                        title = productItem.title ?: "",
                        isLiked = true
                    )
                    detailsViewModel.saveProduct(newLikedProduct)
                    likedProduct.isLiked = true
                    binding.saveBtn.setButtonDrawable(R.drawable.heart_red)
                    binding.saveBtn.isEnabled = false
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.saveBtn.isEnabled = true
                    }, 1000)
                }
            }
        } else {
            // Display liked product details
            binding.productItemTitleTv.text = likedProduct.title
            binding.productItemAmtTv.text = likedProduct.price.toString()
            binding.productItemIv.load(likedProduct.image) {
                crossfade(true)
                placeholder(R.drawable.exclamation)
                transformations(RoundedCornersTransformation())
            }
            binding.saveBtn.visibility = View.GONE
            binding.ivDelete.visibility = View.VISIBLE

            binding.ivDelete.setOnClickListener {
                detailsViewModel.deleteSavedProduct(likedProduct)
            }
        }
    }
}
