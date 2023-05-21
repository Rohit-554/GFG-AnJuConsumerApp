package `in`.jadu.anjuconsumerapp.consumer.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.Product
import `in`.jadu.anjuconsumerapp.consumer.models.repository.ConsumerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetITemListViewModel @Inject constructor(
    private val consumerRepository: ConsumerRepository
) : ViewModel() {
    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()
    private val _getAllItemList = MutableLiveData<List<Product>>()
    val getAllItemList: LiveData<List<Product>>
        get() = _getAllItemList


    init {
        getAllItemList()
    }

    private fun getAllItemList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = consumerRepository.consumerProductList()
            if (response.isSuccessful) {
                Log.d("response", "getAllItemList: ${response.body()}")
                _getAllItemList.postValue(response.body()?.product)
            }
        } catch (e: Exception) {
            Log.d("response", "getAllItemList: ${e.message}")
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message.toString()))
            }
        }

    }

    fun addToCart(phoneNo: String, productId: String, product: Product) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("response", "addToCart: $phoneNo $productId")
                consumerRepository.addToCart(
                    phoneNo, productId,
                    CartTypeDtoItem(
                        _id = product._id,
                        price = product.productPrice,
                        productExpire = product.productExpire,
                        productImageUrl = product.productImageUrl,
                        productName = product.productName,
                        productType = product.productType,
                        quantity = "1",
                        web3Id = product.web3Id,
                        contractAddress = product.contractAddress,
                    )
                )
            } catch (e: Exception) {
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