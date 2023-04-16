package `in`.jadu.anjuconsumerapp.consumer.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.models.repository.ConsumerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsumerCartViewModel@Inject constructor(
    private val consumerRepository: ConsumerRepository
):ViewModel() {
    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()

    private val _getCartItems = MutableLiveData<List<CartTypeDtoItem>>()
    val getCartItems: LiveData<List<CartTypeDtoItem>>
        get() = _getCartItems



    fun getCartItems(phoneNo: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = consumerRepository.getCartItems(phoneNo)
            if (response.isSuccessful) {
                Log.d("response", "getCartItems: ${response.body()}")
                _getCartItems.postValue(response.body())
            }
        } catch (e: Exception) {
            Log.d("response", "getCartItems: ${e.message}")
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