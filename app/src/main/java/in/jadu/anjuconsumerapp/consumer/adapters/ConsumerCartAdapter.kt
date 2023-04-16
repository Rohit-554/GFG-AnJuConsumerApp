package `in`.jadu.anjuconsumerapp.consumer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem

class ConsumerCartAdapter:RecyclerView.Adapter<ConsumerCartAdapter.ConsumerCartViewHolder>() {

    private var count = 0
    var itemTypes: List<CartTypeDtoItem> = emptyList()
    private var listener: OnItemClickListener? = null
    private var listenerIncrease: OnItemIncreaseListener? = null

    interface OnItemClickListener {
        fun onItemClick(cartTypeDtoItem: CartTypeDtoItem)
    }

    interface OnItemIncreaseListener {
        fun onItemIncrease(cartTypeDtoItem: CartTypeDtoItem)
    }

    fun setOnItemIncreaseListener(listener: OnItemIncreaseListener) {
        this.listenerIncrease = listener
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ConsumerCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.iv_vegetables)
        val productName: TextView = itemView.findViewById(R.id.cart_product_name)
        val productPrice: TextView = itemView.findViewById(R.id.cart_product_price)
        val productType: TextView = itemView.findViewById(R.id.cart_product_type)
        val decreaseQuantity: Button = itemView.findViewById(R.id.btn_decrease)
        val increaseQuantity: Button = itemView.findViewById(R.id.btn_increase)
        val tv_inc_dec_quantity: TextView = itemView.findViewById(R.id.inc_dec_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsumerCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_of_cart,parent,false)
        return ConsumerCartViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("consumerCart", "getItemCount: ${itemTypes.size}")
        return itemTypes.size
    }

    override fun onBindViewHolder(holder: ConsumerCartViewHolder, position: Int) {
        val currentItem = itemTypes[position]
        //remove the surrounding quotes
        holder.productName.text = currentItem.productName.removeSurrounding("\"")
        holder.productPrice.text = "₹"+currentItem.price.removeSurrounding("\"") + "/Kg"
        holder.productType.text = currentItem.productType.removeSurrounding("\"")
        val imageLink = getImageLink(currentItem.productImageUrl.removeSurrounding("\""))
        Glide.with(holder.itemView.context).load(imageLink).into(holder.productImage)
        holder.decreaseQuantity.setOnClickListener {
            listener?.onItemClick(currentItem)
            holder.tv_inc_dec_quantity.text = countBtnDec().toString()
        }
        holder.increaseQuantity.setOnClickListener {
            listenerIncrease?.onItemIncrease(currentItem)
            holder.tv_inc_dec_quantity.text = countBtnInc().toString()
        }
    }

    private fun getImageLink(imgId:String): String {
        val baseUrl = "https://firebasestorage.googleapis.com/v0/b/productserver-57d88.appspot.com/"
        val imagePath = "o/$imgId?alt=media"
        return baseUrl + imagePath
    }

    private fun countBtnInc():Int{
        count += 1
        return count
    }
    private fun countBtnDec():Int{
        count -= 1
        return count
    }
}