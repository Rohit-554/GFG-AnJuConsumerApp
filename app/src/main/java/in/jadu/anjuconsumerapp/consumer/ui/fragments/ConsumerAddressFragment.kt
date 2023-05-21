package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import `in` .jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.ui.activity.ConsumerActivity
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerAddressBinding


class ConsumerAddressFragment : Fragment() {
    private lateinit var binding: FragmentConsumerAddressBinding
    private lateinit var bundle: Bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bundle = Bundle()
        binding= FragmentConsumerAddressBinding.inflate(inflater, container, false)
        binding.btnAddAddress.setOnClickListener {
            setupBundle()
            findNavController().navigate(R.id.action_consumerAddressFragment_to_consumerPaymentFragment,bundle)
        }
        (activity as? ConsumerActivity)?.hideBottomNavigation()

        return binding.root
    }

    private fun setupBundle() {
        val amount = arguments?.getString("ethAmount")
        val isFromCart = arguments?.getBoolean("isFromCart")
        val userName = binding.etTextInputName.text.toString()
        val userPhoneNumber = binding.etEnterPhoneNumber.text.toString()
        val flatNo = binding.etFlatNo.text.toString()
        val area = binding.etArea.text.toString()
        val landmark = binding.etLandmark.text.toString()
        val city = binding.etTownCity.text.toString()
        val pincode = binding.etPincode.text.toString()
        val state = binding.etState.text.toString()
        val cartPriceInRupees = arguments?.getString("totalPriceRupees")

        bundle.putString("ethAmount", amount)
        bundle.putBoolean("isFromCart", isFromCart!!)
        bundle.putString("userName", userName)
        bundle.putString("userPhoneNumber", userPhoneNumber)
        bundle.putString("flatNo", flatNo)
        bundle.putString("area", area)
        bundle.putString("landmark", landmark)
        bundle.putString("city", city)
        bundle.putString("pincode", pincode)
        bundle.putString("state", state)
        bundle.putString("totalPriceRupees", cartPriceInRupees)


    }


}