package com.appsvit.productlist_app.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.databinding.ProductItemBinding
import com.appsvit.productlist_app.utils.NewsItemClicksListener

class ProductListAdapter(
    private val productList: List<ProductItem>,
    private val listener: NewsItemClicksListener
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ProductItem) {
            binding.productItemTitleTv.text = item.title
            binding.productItemAmtTv.text = item.price.toString()
            binding.productItemIv.load(item.image) {
                crossfade(true)
                transformations(RoundedCornersTransformation())
            }

            binding.saveBtn.setOnClickListener {
                listener.onItemClicked(product = item, likedProduct = LikedProduct())
            }


        }

    }
}