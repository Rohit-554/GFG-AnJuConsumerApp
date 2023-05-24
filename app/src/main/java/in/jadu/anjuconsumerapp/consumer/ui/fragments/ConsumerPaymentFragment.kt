package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.OrderProduct
import `in`.jadu.anjuconsumerapp.consumer.ui.activity.ConsumerActivity
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.CartAndPurchaseViewModel
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.ContractOperationViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerPaymentBinding
import `in`.jadu.anjuconsumerapp.kvstorage.KvStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.protocol.exceptions.TransactionException
import java.math.BigInteger
import javax.inject.Inject

@AndroidEntryPoint
class ConsumerPaymentFragment : Fragment() {
    private lateinit var binding: FragmentConsumerPaymentBinding
    private lateinit var bundle: Bundle
    private val contractOperationViewModel: ContractOperationViewModel by viewModels()
    private val cartAndPurchaseViewModel: CartAndPurchaseViewModel by viewModels()
    private var cartAmountInWei: Long? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userAddress: String = ""
    private var userName: String = ""
    private var userPhoneNumber: String = ""
    private lateinit var walletAddress: String
    private var contractAddress: String? = ""
    private var cartPriceInRupees:String = ""
    private lateinit var KvStorage: KvStorage
    private var isWalletCreated: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bundle = Bundle()
        binding = FragmentConsumerPaymentBinding.inflate(inflater, container, false)
        getBundleValues()
        KvStorage = KvStorage(requireContext())
        isWalletCreated = KvStorage.storageGetBoolean("isWalletCreated")
        if(isWalletCreated){
            contractOperationViewModel
        }else{
            Snackbar.make(requireView(), "Wallet not created", Snackbar.LENGTH_SHORT).show()
        }
        KvStorage = KvStorage(requireContext())
        walletAddress = KvStorage.storageGetString("walletAddress").toString()
        contractAddress = KvStorage.storageGetString("contractAddress").toString()
        (activity as? ConsumerActivity)?.hideBottomNavigation()
        binding.btnPlaceOrder.setOnClickListener {
            updateUiOnProcess()
            addPurchasedItemOnBlockChain()
//            addPurchasedItemOnServer()
        }
        //listen to event
        lifecycleScope.launch { 
            cartAndPurchaseViewModel.mainEvent.collect { event ->
                when(event){
                    is CartAndPurchaseViewModel.MainEvent.Success -> {
                        Log.d("TAG", "onCreateView: ${event}")
                    }
                    is CartAndPurchaseViewModel.MainEvent.Error -> {
                        Log.d("TAG", "onCreateView: ${event}")
                        displayErrorSnackBar(event.error)
                        updateUiOnError()
                    }
                }
            }
        }
        
        return binding.root
    }

    private fun updateUiOnProcess(){
        binding.llConsumerPayment.alpha = 0.5f
        binding.placeOrderProgress.visibility = View.GONE
        binding.btnPlaceOrder.text = getString(R.string.in_process)
        binding.orderProcessingLoaderAnimator.visibility = View.VISIBLE
    }

    private fun updateUiOnError(){
        binding.llConsumerPayment.alpha = 1f
        binding.placeOrderProgress.visibility = View.GONE
        binding.btnPlaceOrder.text = getString(R.string.retry)
        binding.orderProcessingLoaderAnimator.visibility = View.GONE
    }

    private fun getBundleValues() {
        val amount = arguments?.getString("ethAmount")
        val isFromCart = arguments?.getBoolean("isFromCart")
        userName = arguments?.getString("userName").toString()
        userPhoneNumber = arguments?.getString("userPhoneNumber").toString()
        val flatNo = arguments?.getString("flatNo")
        val area = arguments?.getString("area")
        val landmark = arguments?.getString("landmark")
        val city = arguments?.getString("city")
        val pincode = arguments?.getString("pincode")
        val state = arguments?.getString("state")
        cartPriceInRupees = arguments?.getString("totalPriceRupees").toString()
        Log.d(
            "rohitdh",
            "getBundleValues: $userName, $userPhoneNumber, $flatNo, $area, $landmark, $city, $pincode, $state ,$amount, $isFromCart"
        )

        val ethAmount = amount?.toDouble()
        cartAmountInWei = ethAmount?.times(1000000000000000000)?.toLong()

        val userAddressWithName = "$userName, $flatNo, $area, $landmark, $city, $pincode, $state"
        userAddress = "$flatNo, $area, $landmark, $city, $pincode, $state"

        binding.userAddressPreviewValue.text = userAddressWithName
        binding.tvUserAddress.text = userAddress
        binding.tvUserName.text = userName
        binding.tvTotalAmountValueRupees.text = "₹" + cartPriceInRupees
        bundle.putString("ethAmount", amount)
        bundle.putBoolean("isFromCart", isFromCart!!)
        bundle.putString("userName", userName)
        bundle.putString("userPhoneNumber", userPhoneNumber)
        bundle.putString("userAddress", userAddress)
        bundle.putString("totalPriceRupees", cartPriceInRupees)
    }

    private fun addPurchasedItemOnBlockChain() {
        contractOperationViewModel.contractInstance.observe(requireActivity()) { instance ->
            cartAndPurchaseViewModel.getCartItems.observe(requireActivity()) { ItemList ->
                val itemIds = ItemList.map { BigInteger(it.web3Id.removeSurrounding("\"")) }
                val productName = ItemList.map { it.productName.removeSurrounding("\"") }
                val productQuantities =
                    ItemList.map { BigInteger(it.quantity.removeSurrounding("\"")) }
                val farmersContractAddresses =
                    ItemList.map { it.contractAddress.removeSurrounding("\"") }
                val prices = ItemList.map { BigInteger(it.price.removeSurrounding("\"")) }

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val transactionReceipt = instance?.orderProduct(
                            itemIds,
                            productName,
                            productQuantities,
                            farmersContractAddresses,
                            userAddress,
                            prices,
                            BigInteger.valueOf(cartAmountInWei!!)
                        )?.send()
                        if (transactionReceipt?.isStatusOK!!) {
                            addPurchasedItemOnServer()
                            withContext(Dispatchers.Main) {
                                Log.d(
                                    "WalletConnectFragmentt",
                                    "addPurchasedItemOnBlockChain: ${transactionReceipt.transactionHash}"
                                )
                                findNavController().navigate(R.id.action_consumerPaymentFragment_to_confirmPaymentFragment)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                updateUiOnError()
                                displayErrorSnackBar("Transaction Failed")
                            }
                        }
                    } catch (e: TransactionException) {
                        updateUiOnError()
                        displayErrorSnackBar("Transaction Failed")
                        Log.d("transactionFailed", e.toString())
                    }
                }
            }
        }
    }

    private fun addPurchasedItemOnServer() {
        Log.d("testPurchase", "addPurchasedItemOnServer: $userPhoneNumber, $userAddress, $userName, $contractAddress, $walletAddress, $cartPriceInRupees")
        auth.currentUser?.phoneNumber?.substring(3)
            ?.let {
                cartAndPurchaseViewModel.purchaseProductFromCart(
                    it,
                    OrderProduct(
                        userAddress,
                        userName,
                        userPhoneNumber,
                        contractAddress!!,
                        walletAddress,
                        cartPriceInRupees,
                        "pending"
                    )
                )
            }
    }
    private fun displayErrorSnackBar(msg: String) {
        val snackBar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            msg,
            Snackbar.LENGTH_SHORT
        )

        snackBar.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_text_color))
        snackBar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.surface))
        snackBar.show()
    }


}