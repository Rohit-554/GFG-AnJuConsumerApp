package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import `in` .jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.ui.activity.ConsumerActivity
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerPaymentBinding


class ConsumerPaymentFragment : Fragment() {
    private lateinit var binding: FragmentConsumerPaymentBinding
    private lateinit var bundle: Bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bundle = Bundle()
        binding = FragmentConsumerPaymentBinding.inflate(inflater, container, false)
        getBundleValues()
        (activity as? ConsumerActivity)?.hideBottomNavigation()
        binding.btnPlaceOrder.setOnClickListener {
            findNavController().navigate(R.id.action_consumerPaymentFragment_to_walletFragment,bundle)
        }
        return binding.root
    }
    private fun getBundleValues(){
        val amount = arguments?.getString("ethAmount")
        val isFromCart = arguments?.getBoolean("isFromCart")
        val userName = arguments?.getString("userName")
        val userPhoneNumber = arguments?.getString("userPhoneNumber")
        val flatNo = arguments?.getString("flatNo")
        val area = arguments?.getString("area")
        val landmark = arguments?.getString("landmark")
        val city = arguments?.getString("city")
        val pincode = arguments?.getString("pincode")
        val state = arguments?.getString("state")
        val cartPriceInRupees = arguments?.getString("totalPriceRupees")
        Log.d("rohitdh", "getBundleValues: $userName, $userPhoneNumber, $flatNo, $area, $landmark, $city, $pincode, $state ,$amount, $isFromCart")

        val userAddressWithName = "$userName, $flatNo, $area, $landmark, $city, $pincode, $state"
        val userAddress = "$flatNo, $area, $landmark, $city, $pincode, $state"

        binding.userAddressPreviewValue.text = userAddressWithName
        binding.tvUserAddress.text = userAddress
        binding.tvUserName.text = userName
        binding.tvTotalAmountValueRupees.text = "₹"+cartPriceInRupees
        bundle.putString("ethAmount", amount)
        bundle.putBoolean("isFromCart", isFromCart!!)
        bundle.putString("userName", userName)
        bundle.putString("userPhoneNumber", userPhoneNumber)
        bundle.putString("userAddress", userAddress)
        bundle.putString("totalPriceRupees", cartPriceInRupees)
    }

    //product id
    //address user
    //user name
    //user phone number
    //total price
    //user contract address
    //user Web3addressId
    //payment amount
    //farmerid
    //payment status

}