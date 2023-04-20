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
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDto
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem

class PurchasedProductAdapter : Adapter<PurchasedProductAdapter.PurchasedProductViewHolder>() {

    var purchasedProductList:List<CartTypeDtoItem> = emptyList()

    class PurchasedProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productImage:ImageView = itemView.findViewById(R.id.iv_vegetables)
        val productName: TextView = itemView.findViewById(R.id.tv_product_name)
        val productPrice: TextView = itemView.findViewById(R.id.cart_product_price)
        val quantity:TextView = itemView.findViewById(R.id.cart_quantity)
        val expireDate: TextView = itemView.findViewById(R.id.cart_expire_date)
        val productType: TextView = itemView.findViewById(R.id.cart_product_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasedProductViewHolder {
        //inflate the layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchased_list,parent,false)
        return PurchasedProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("purchaseProduct", "getItemCount: $purchasedProductList")
        return purchasedProductList.size

    }

    override fun onBindViewHolder(holder: PurchasedProductViewHolder, position: Int) {
        val currentItem = purchasedProductList[position]
        holder.productName.text = currentItem.productName.removeSurrounding("\"")
        holder.productPrice.text = "₹"+currentItem.price.removeSurrounding("\"") + "/Kg"
        holder.quantity.text = currentItem.quantity.toString().removeSurrounding("\"")
        holder.expireDate.text = currentItem.productExpire.removeSurrounding("\"")
        holder.productType.text = currentItem.productType.removeSurrounding("\"")
    }
}
