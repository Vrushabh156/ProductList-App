package com.appsvit.productlist_app.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.appsvit.productlist_app.data.models.LikedProduct
import com.appsvit.productlist_app.data.models.ProductItem
import com.appsvit.productlist_app.databinding.SaveProductItemBinding
import com.appsvit.productlist_app.utils.NewsItemClicksListener

class SavedProductListAdapter(
    private val productList: List<LikedProduct>,
    private val listener: NewsItemClicksListener
) : RecyclerView.Adapter<SavedProductListAdapter.SavedProductVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedProductVH {
        val binding = SaveProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedProductVH(binding)
    }

    override fun onBindViewHolder(holder: SavedProductVH, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size

    inner class SavedProductVH(private val binding: SaveProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: LikedProduct) {
            binding.productItemTitleTv.text = item.title
            binding.productItemAmtTv.text = item.price.toString()
            binding.productItemIv.load(item.image) {
                crossfade(true)
                transformations(RoundedCornersTransformation())
            }

            binding.saveBtn.setOnClickListener {
                listener.onItemClicked(likedProduct = item, product = ProductItem())
            }
        }
    }
}