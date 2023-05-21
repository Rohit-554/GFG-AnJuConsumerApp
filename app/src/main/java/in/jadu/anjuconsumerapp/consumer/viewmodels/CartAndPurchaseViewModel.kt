package `in`.jadu.anjuconsumerapp.consumer.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.OrderProduct
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

init {
    getCartItems(phoneNo)
    getPurchasedItems(phoneNo)
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
            } catch (e: Exception) {
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

    fun orderProduct(phoneNo: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            consumerRepository.orderProduct(phoneNo)
        } catch (e: Exception) {
            Log.d("response", "orderProduct: ${e.message}")
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message.toString()))
            }
        }
    }


    sealed class MainEvent {
        data class Error(val error: String) : MainEvent()
        data class Success(val message: String) : MainEvent()
    }

}