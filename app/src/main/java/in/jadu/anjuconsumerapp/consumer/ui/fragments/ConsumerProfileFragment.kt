package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.ui.activity.ConsumerActivity
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerProfileBinding


class ConsumerProfileFragment : Fragment() {
    private lateinit var binding: FragmentConsumerProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsumerProfileBinding.inflate(inflater,container,false)
        binding.btnOrders.setOnClickListener {
            findNavController().navigate(R.id.action_consumerProfileFragment_to_purchasedProductFragment)
        }
        return binding.root
    }




}