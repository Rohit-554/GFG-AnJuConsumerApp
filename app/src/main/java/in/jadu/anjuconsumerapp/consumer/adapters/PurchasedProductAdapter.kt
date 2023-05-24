package `in`.jadu.anjuconsumerapp.consumer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDto
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.ProductOrderDetails
import `in`.jadu.anjuconsumerapp.utility.UtilityFunctions

class PurchasedProductAdapter( private val listener: OnItemClickListener) : Adapter<PurchasedProductAdapter.PurchasedProductViewHolder>() {

    var purchasedProduct: ProductOrderDetails? = null
    inner class PurchasedProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productName: TextView = itemView.findViewById(R.id.cart_product_name_text)
        val ivProductImage:ImageView = itemView.findViewById(R.id.iv_product_image)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasedProductViewHolder {
        //inflate the layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchased_list,parent,false)
        return PurchasedProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return purchasedProduct?.orders?.products?.size ?: 0

    }

    override fun onBindViewHolder(holder: PurchasedProductViewHolder, position: Int) {
        val currentItem = purchasedProduct?.orders?.products?.get(position)
        holder.productName.text = currentItem?.product?.productName?.removeSurrounding("\"")
         val imageLink = currentItem?.product?.productImageUrl?.removeSurrounding("\"")
             ?.let { getImageLink(it) }
        Glide.with(holder.itemView.context).load(imageLink).into(holder.ivProductImage)
    }
    private fun getImageLink(imgId: String): String {
        val baseUrl = "https://firebasestorage.googleapis.com/v0/b/productserver-57d88.appspot.com/"
        val imagePath = "o/$imgId?alt=media"
        return baseUrl + imagePath
    }
    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }
}
