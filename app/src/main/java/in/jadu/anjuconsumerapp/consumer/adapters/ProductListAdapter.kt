package `in`.jadu.anjuconsumerapp.consumer.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.Product
import `in`.jadu.anjuconsumerapp.utility.UtilityFunctions

class ProductListAdapter():RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {
    var itemTypes: List<Product> = emptyList()
    private var listener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemButtonClicked(product: Product)
        fun onItemClicked(product: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage:ImageView = itemView.findViewById(R.id.iv_vegetables)
        val productName: TextView = itemView.findViewById(R.id.item_product_name)
        val productPrice:TextView = itemView.findViewById(R.id.farmer_price)
        val expireDate:TextView = itemView.findViewById(R.id.tv_expire_date)
        val btnAddToCart:CardView = itemView.findViewById(R.id.btn_add_to_cart)
        val itemListCardView:CardView = itemView.findViewById(R.id.item_list_card_view)
        val ivAddToCart:ImageView = itemView.findViewById(R.id.iv_add_to_cart)
//        val productType:TextView = itemView.findViewById(R.id.ItemProductType)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_of_products,parent,false)
        return ProductListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemTypes.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val currentItem = itemTypes[position]
        //remove the surrounding quotes
        holder.productName.text = currentItem.productName.removeSurrounding("\"")
        holder.productPrice.text = "₹"+currentItem.productPrice.removeSurrounding("\"")
        holder.expireDate.text = currentItem.productExpire.removeSurrounding("\"")
//        holder.productType.text = currentItem.productType.removeSurrounding("\"")

        val imageLink = getImageLink(currentItem.productImageUrl.removeSurrounding("\""))
        UtilityFunctions.getImageBitmap(holder.itemView.context,imageLink){bitmap->
            if(bitmap!=null){
                Palette.from(bitmap).generate { palette->
                    val dominantColor = palette?.getVibrantColor(ContextCompat.getColor(holder.itemView.context,R.color.blueColor))
                    val lightVibrant = palette?.getLightVibrantColor(ContextCompat.getColor(holder.itemView.context,R.color.blueColor))
                    if (lightVibrant != null) {
                        holder.itemListCardView.setCardBackgroundColor(lightVibrant)
                        holder.btnAddToCart.setCardBackgroundColor(UtilityFunctions.increaseColorStrength(lightVibrant))
                    }
                }
                holder.productImage.setImageBitmap(bitmap)
            }

        }
//        Glide.with(holder.itemView.context)
//            .load(imageLink)
//            .apply(RequestOptions().override(100, 100))
//            .into(holder.productImage)
        holder.btnAddToCart.setOnClickListener {
            holder.ivAddToCart.setImageResource(R.drawable.tick_icon)
            listener?.onItemButtonClicked(currentItem)
        }

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(currentItem)
        }

    }
    private fun getImageLink(imgId:String): String {
        val baseUrl = "https://firebasestorage.googleapis.com/v0/b/productserver-57d88.appspot.com/"
        val imagePath = "o/$imgId?alt=media"
        return baseUrl + imagePath
    }
}
