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
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerPreviewPaymentBinding
import java.math.BigDecimal

@AndroidEntryPoint
class ConsumerPreviewPaymentFragment : Fragment() {
    private lateinit var binding: FragmentConsumerPreviewPaymentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConsumerCartAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var bundle: Bundle
    private val eachProductPrice:Double = 0.0
    private var finalCartPrice:Double = 0.0
    private val consumerCartViewModel: ConsumerCartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bundle = Bundle()
        binding = FragmentConsumerPreviewPaymentBinding.inflate(inflater, container, false)
        recyclerView = binding.rvCheckoutProducts
        auth = FirebaseAuth.getInstance()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        adapter = ConsumerCartAdapter()
        consumerCartViewModel.getCartItems(auth.currentUser?.phoneNumber.toString().substring(3))
        consumerCartViewModel.getCartItems.observe(viewLifecycleOwner){ it ->
            if(it.isEmpty()){
                Toast.makeText(requireContext(), "No Products Available Now", Toast.LENGTH_SHORT).show()
            }else{
                Log.d("consumerCart", "onCreateView: $it")
                adapter.itemTypes = it
                it.forEach {cartType->
                    finalCartPrice += cartType.price.removeSurrounding("\"").toDouble() * cartType.quantity.toDouble()
                }
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            binding.tvAmountValue.text = "₹$finalCartPrice"
            calculateEth(finalCartPrice)
            Log.d("finalCartPrice", "onCreateView: $finalCartPrice")
        }

        binding.progressBarCheckout.visibility = View.GONE

        binding.btnPayEther.setOnClickListener {
            binding.progressBarCheckout.visibility = View.VISIBLE
            binding.btnPayEther.visibility = View.GONE
            findNavController().navigate(R.id.action_consumerPreviewPaymentFragment_to_walletFragment, bundle)
        }
        clickEvents()

        return binding.root
    }

    private fun clickEvents(){
        adapter.setOnItemClickListener(object : ConsumerCartAdapter.OnItemClickListener {
            override fun onItemClick(cartTypeDtoItem: CartTypeDtoItem) {
            }


        })

        adapter.setOnItemIncreaseListener(object : ConsumerCartAdapter.OnItemIncreaseListener {
            override fun onItemIncrease(cartTypeDtoItem: CartTypeDtoItem) {
                finalCartPrice -= cartTypeDtoItem.price.removeSurrounding("\"").toDouble()
            }
        })
    }

    fun calculateEth(finalCartPrice: Double) {
        val ethPrice = 171666.03
        val ethAmountForEach = 1/ethPrice
        val ethAmount = ethAmountForEach * finalCartPrice
        val newAmount = ethAmount.toDouble()
        val formatted = "%.5f".format(BigDecimal(newAmount).setScale(3, BigDecimal.ROUND_DOWN))
        binding.tvEthsRequiredValue.text = "$formatted ETH"
        bundle.putString("ethAmount", formatted)
        bundle.putBoolean("isFromCart", true)

    }

}