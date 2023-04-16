package `in`.jadu.anjuconsumerapp.consumer.commonuis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.consumer.commonuis.viewmodels.PhoneVerificationViewModel
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.databinding.FragmentPhoneVerificationBinding
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class PhoneVerificationFragment : Fragment() {
    private lateinit var binding: FragmentPhoneVerificationBinding
    private lateinit var auth: FirebaseAuth
    private var farmerNumber: String? = ""
    private var countryCodeFarmerNumber: String? = ""


    lateinit var phoneVerificationViewModel: PhoneVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneVerificationBinding.inflate(inflater, container, false)
        phoneVerificationViewModel = ViewModelProvider(this)[PhoneVerificationViewModel::class.java]
        auth = FirebaseAuth.getInstance()
        getOtp()

        lifecycleScope.launch {
            phoneVerificationViewModel.mainEvent.collect() { event ->
                when (event) {
                    is PhoneVerificationViewModel.MainEvent.GetUser -> {
                        updateUiAfterVerification()
                    }

                    is PhoneVerificationViewModel.MainEvent.Error -> {
                        updateUiOnError(event.error)
                    }
                }
            }
        }
        return binding.root
    }

    private fun getOtp() {
        binding.btnVerify.setOnClickListener {
            if (checkIfEditTextIsNotEmpty()) {
                val phoneNumber = binding.etEnterPhoneNumber.text?.trim().toString()
                //add +91 to phone number
                farmerNumber = phoneNumber
                countryCodeFarmerNumber = "+91$phoneNumber"
                binding.lottieProgress.visibility = View.VISIBLE
                binding.btnVerify.visibility = View.GONE
                sendOtp(countryCodeFarmerNumber!!)
            } else {
                binding.etEnterPhoneNumber.error = "Enter Phone Number"
            }
        }
    }


    private fun checkIfEditTextIsNotEmpty(): Boolean {
        return binding.etEnterPhoneNumber.text.toString()
            .isNotEmpty() && binding.etEnterPhoneNumber.text.toString().length == 10
    }

    private fun sendOtp(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            phoneVerificationViewModel.signInWithPhoneAuthCredential(p0)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                updateUiOnError(e.toString())
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                updateUiOnError(e.toString())
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
                updateUiOnError(e.toString())
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
//            storedVerificationId = verificationId
//            resendToken = token
            //send the verficaiton id to the next fragment
            val bundle = Bundle()
            bundle.putString("verificationId", verificationId)
            findNavController().navigate(
                R.id.action_phoneVerificationFragment_to_confirmOtpFragment,
                bundle
            )
        }
    }

    private fun updateUiOnError(e: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.lottieProgress.visibility = View.GONE
        binding.btnVerify.visibility = View.VISIBLE
        binding.tvError.text = e
    }

    private fun updateUiAfterVerification() {
        binding.lottieProgress.visibility = View.GONE
        binding.btnVerify.visibility = View.VISIBLE
        findNavController().navigate(R.id.action_phoneVerificationFragment_to_confirmOtpFragment)
    }
}