package `in`.jadu.anjuconsumerapp.consumer.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.OrderProduct
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.ProductOrderDetails
import `in`.jadu.anjuconsumerapp.consumer.models.repository.ConsumerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartAndPurchaseViewModel@Inject constructor(
    private val consumerRepository: ConsumerRepository
):ViewModel() {
    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()

    val phoneNo = FirebaseAuth.getInstance().currentUser?.phoneNumber.toString().substring(3)
    private val _getCartItems = MutableLiveData<List<CartTypeDtoItem>>()
    val getCartItems: LiveData<List<CartTypeDtoItem>>
        get() = _getCartItems

    private val _getPurchasedItems = MutableLiveData<List<CartTypeDtoItem>>()
    val getPurchasedItems: LiveData<List<CartTypeDtoItem>>
        get() = _getPurchasedItems

    private val _getOrderedProducts = MutableLiveData<ProductOrderDetails>()
    val getOrderedProducts: LiveData<ProductOrderDetails>
        get() = _getOrderedProducts

init {
    getCartItems(phoneNo)
//    getPurchasedItems(phoneNo)
    getOrderedProducts(phoneNo)
}

    fun getCartItems(phoneNo: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = consumerRepository.getCartItems(phoneNo)
            if (response.isSuccessful) {
                Log.d("responsecart", "getCartItems: ${response.body()}")
                _getCartItems.postValue(response.body())
            }else{
                Log.d("responsecart", "getCartItems: ${response.body()}")
                _getCartItems.postValue(response.body())
            }
        } catch (e: Exception) {
            Log.d("response", "getCartItems: ${e.message}")
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message.toString()))
            }
        }
    }

    fun purchaseProductFromCart(phoneNo:String,orderProduct: OrderProduct){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                consumerRepository.purchasedProductList(phoneNo,orderProduct)
                Log.d("response", "purchaseProductFromCart: ${orderProduct}")
                viewModelScope.launch {
                    mainEventChannel.send(MainEvent.Success("Product Purchased Successfully"))
                }
            } catch (e: Exception) {
                Log.d("responseerror", "purchaseProductFromCart: ${e.message}")
                viewModelScope.launch {
                    mainEventChannel.send(MainEvent.Error(e.message.toString()))
                }
            }
        }
    }

    private fun getPurchasedItems(phoneNo: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = consumerRepository.getPurchasedProductList(phoneNo)
            if (response.isSuccessful) {
                Log.d("responsecart", "getPurchasedItems: ${response.body()}")
                _getPurchasedItems.postValue(response.body())
            }else{
                Log.d("responsecart", "getPurchasedItems: ${response.body()}")
                _getPurchasedItems.postValue(response.body())
            }
        } catch (e: Exception) {
            Log.d("response", "getPurchasedItems: ${e.message}")
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message.toString()))
            }
        }
    }

    private fun getOrderedProducts(phoneNo: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = consumerRepository.orderProduct(phoneNo)
            if(response.isSuccessful) {
                Log.d("responsecart", "getOrderedProducts: ${response.body()}")
                _getOrderedProducts.postValue(response.body())
                viewModelScope.launch {
                    mainEventChannel.send(MainEvent.Success("Order Placed Successfully"))
                }
            }else{
                Log.d("responsecart", "getOrderedProducts: ${response.body()}")
                viewModelScope.launch {
                    mainEventChannel.send(MainEvent.Error("Order Placed Failed"))
                }
            }
        } catch (e: Exception) {
            Log.d("response", "orderProduct: ${e.message}")
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message.toString()))
            }
        }
    }
    fun getImageBitmap(context: Context, imgUrl: String, callback: (Bitmap?) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(imgUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Optional: handle when the resource is cleared
                }
            })
    }
    fun getImageLink(imgId: String): String {
        val baseUrl = "https://firebasestorage.googleapis.com/v0/b/productserver-57d88.appspot.com/"
        val imagePath = "o/$imgId?alt=media"
        return baseUrl + imagePath
    }

    sealed class MainEvent {
        data class Error(val error: String) : MainEvent()
        data class Success(val message: String) : MainEvent()
    }

}