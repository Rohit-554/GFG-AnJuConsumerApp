package `in`.jadu.anjuconsumerapp.consumer.commonuis.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.jadu.anjuconsumerapp.consumer.models.repository.ConsumerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Provider
import java.security.Security
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PhoneVerificationViewModel @Inject constructor(
    private val consumerRepository: ConsumerRepository
) : ViewModel() {
    //get the auth
    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()

    private val auth = FirebaseAuth.getInstance()

init {
    setupBouncyCastle()
}
     fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    viewModelScope.launch {
                        val event = user?.let { MainEvent.GetUser(it) }
                        if (event != null) {
                            mainEventChannel.send(event)
                        }
                    }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        viewModelScope.launch {
                            mainEventChannel.send(MainEvent.Error(task.toString()))
                        }
                    }
                }
            }
    }

    fun uploadPhoneNo(phoneNo: String) = viewModelScope.launch(Dispatchers.IO) {
        consumerRepository.setPhone(phoneNo)
    }

    private fun setupBouncyCastle() {
        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?:
            return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            return
        }
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    //create a sealed class to send the signInWithPhoneAuthCredential(p0)
    sealed class MainEvent {
        data class GetUser(val user: FirebaseUser) : MainEvent()
        data class Error(val error: String) : MainEvent()
    }


}