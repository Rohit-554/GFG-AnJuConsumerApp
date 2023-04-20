package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerTrackOrderBinding

@AndroidEntryPoint
class ConsumerTrackOrderFragment : Fragment() {
    private lateinit var binding: FragmentConsumerTrackOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsumerTrackOrderBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener {
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}