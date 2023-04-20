package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.databinding.FragmentConfirmPaymentBinding


class ConfirmPaymentFragment : Fragment() {
   private lateinit var binding: FragmentConfirmPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmPaymentBinding.inflate(inflater, container, false)
        binding.vieworder.setOnClickListener {
            findNavController().navigate(R.id.action_confirmPaymentFragment_to_purchasedProductFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}