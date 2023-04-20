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
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.CartAndPurchaseViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerPreviewPaymentBinding
import java.math.BigDecimal
import java.math.RoundingMode

@AndroidEntryPoint
class ConsumerPreviewPaymentFragment : Fragment() {
    private lateinit var binding: FragmentConsumerPreviewPaymentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConsumerCartAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var bundle: Bundle
    private val eachProductPrice:Double = 0.0
    private var finalCartPrice:Double = 0.0
    private val cartAndPurchaseViewModel: CartAndPurchaseViewModel by viewModels()
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
        cartAndPurchaseViewModel.getCartItems.observe(viewLifecycleOwner){ it ->
            if(it.isNullOrEmpty()){
                binding.tvEmptyCart.visibility = View.VISIBLE
                binding.viewLine.visibility = View.GONE
                binding.tvPayment.visibility = View.GONE
                binding.viewLine2.visibility = View.GONE
                binding.cardViewPayment.visibility = View.GONE
                binding.btnPayEther.visibility = View.GONE
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

        binding.tvTrack.setOnClickListener{
            findNavController().navigate(R.id.action_consumerPreviewPaymentFragment_to_purchasedProductFragment)
        }
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

        val ethPrice = BigDecimal("171666.03")
        val ethAmountForEach = BigDecimal("1").divide(ethPrice, 18, RoundingMode.HALF_UP)
        val ethAmount = ethAmountForEach.multiply(BigDecimal(finalCartPrice.toString()))
        Log.d("ethAmount", "calculateEth: $ethAmount")
        val newAmount = ethAmount.toLong()
        val formatted = "%.10f".format(BigDecimal(newAmount).setScale(3, BigDecimal.ROUND_DOWN))
        binding.tvEthsRequiredValue.text = "$ethAmount ETH"
        bundle.putString("ethAmount", ethAmount.toString())
        bundle.putBoolean("isFromCart", true)

    }

}