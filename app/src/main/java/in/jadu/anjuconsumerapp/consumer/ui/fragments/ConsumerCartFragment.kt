package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.adapters.ConsumerCartAdapter
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.ConsumerCartViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerCartBinding

@AndroidEntryPoint
class ConsumerCartFragment : Fragment() {
    private lateinit var binding: FragmentConsumerCartBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:ConsumerCartAdapter
    private lateinit var auth: FirebaseAuth
    private val consumerCartViewModel: ConsumerCartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConsumerCartBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        recyclerView = binding.rvConsumerCart
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        adapter = ConsumerCartAdapter()
        //get the number from 3
        consumerCartViewModel.getCartItems(auth.currentUser?.phoneNumber.toString().substring(3))
        consumerCartViewModel.getCartItems.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                Toast.makeText(requireContext(), "No Products Available Now", Toast.LENGTH_SHORT).show()
            }else{
                Log.d("consumerCart", "onCreateView: $it")
                adapter.itemTypes = it
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        binding.btnCheckout.setOnClickListener {

        }
        adapter.setOnItemClickListener(object : ConsumerCartAdapter.OnItemClickListener {
            override fun onItemClick(cartTypeDtoItem: CartTypeDtoItem) {

            }


        })

        adapter.setOnItemIncreaseListener(object : ConsumerCartAdapter.OnItemIncreaseListener {
            override fun onItemIncrease(cartTypeDtoItem: CartTypeDtoItem) {

            }
        })


        return binding.root
    }

}