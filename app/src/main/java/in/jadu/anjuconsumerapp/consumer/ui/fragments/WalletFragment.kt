package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.CartAndPurchaseViewModel
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.WalletConnectViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentWalletBinding
import `in`.jadu.anjuconsumerapp.kvstorage.KvStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@AndroidEntryPoint
class WalletFragment : Fragment() {
    private lateinit var binding: FragmentWalletBinding

    @Inject
    lateinit var KvStorage: KvStorage
    private lateinit var walletName: String
    private lateinit var walletPassword: String
    private lateinit var walletAddress: String
    private var isWalletCreated: Boolean = false
    private val walletConnectViewModel: WalletConnectViewModel by viewModels()
    private val cartAndPurchaseViewModel: CartAndPurchaseViewModel by viewModels()
    private var balance = 0.0
    private var isFromCart: Boolean? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        walletName = KvStorage.storageGetString("walletName").toString()
        walletPassword = KvStorage.storageGetString("walletPassword").toString()
        walletAddress = KvStorage.storageGetString("walletAddress").toString()
        isWalletCreated = KvStorage.storageGetBoolean("isWalletCreated")

        if (isWalletCreated) {
            updateUIOnWalletCreation()
            handleEvent()
        } else {
            updateUIWhenWalletNotCreated()
        }
        binding.btnSuccess.setOnClickListener {
            binding.lottieAnimationWallet.visibility = View.VISIBLE
            binding.btnSuccess.visibility = View.GONE
            binding.tvSuccessText.text = getString(R.string.creating_wallet_please_wait)
            findNavController().navigate(R.id.action_walletFragment_to_walletConnectFragment)
        }
        binding.fetchBalance.setOnClickListener {
            walletConnectViewModel.retrieveBalance()
        }

        binding.btnSendMoney.setOnClickListener {
            binding.TransefeeringProgressBar.visibility = View.VISIBLE
            binding.btnSendMoney.visibility = View.GONE

            transferFund()
        }
        binding.btnAddMoney.setOnClickListener {
            depositFund()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Check if there is a navigation from the payment fragment
        if (findNavController().previousBackStackEntry?.destination?.id == R.id.consumerPreviewPaymentFragment) {
            // Call the function to set the EditText fields
            handlePaymentFromCart()
        }
    }

    private fun handlePaymentFromCart(){
        val amount = arguments?.getString("ethAmount")
        isFromCart = arguments?.getBoolean("isFromCart")
        binding.etPeerWalletAddress.requestFocus()
        binding.etPeerWalletAddress.setText("0x0B3a6557AD46e877CeA3d7670AFB7D53211e6d63")
        binding.etPeerBalanceSend.requestFocus()
        binding.etPeerBalanceSend.setText(amount)
    }


    fun handleEvent() {
        lifecycleScope.launch {
            walletConnectViewModel.walletConnectEvent.collect { event ->
                when (event) {
                    is WalletConnectViewModel.WalletConnectEvent.ConnectToWalletMessage -> {
                        Toast.makeText(requireContext(), "Connected", Toast.LENGTH_SHORT).show()
                    }

                    is WalletConnectViewModel.WalletConnectEvent.ConnectToWalletError -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }

                    is WalletConnectViewModel.WalletConnectEvent.WalletCreatedSuccessfully -> {

                    }

                    is WalletConnectViewModel.WalletConnectEvent.WalletCreatedError -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                        Log.d("WalletConnectFragment", "handleEvent: ${event.error}")
                    }

                    is WalletConnectViewModel.WalletConnectEvent.WalletBalanceSuccessfully -> {
                        balance = event.balance.toDouble()
                        val formatted = "%.5f".format(BigDecimal(event.balance).setScale(5, BigDecimal.ROUND_DOWN))
                        binding.walletBalanceValue.text = formatted
                    }

                    is WalletConnectViewModel.WalletConnectEvent.WalletBalanceError -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }

                    is WalletConnectViewModel.WalletConnectEvent.WalletTransactionSuccessfully -> {
                        Toast.makeText(requireContext(), "Transaction Successful", Toast.LENGTH_SHORT).show()
                           binding.TransefeeringProgressBar.visibility = View.GONE
                            binding.btnSendMoney.visibility = View.VISIBLE
                            if (isFromCart == true) {
                                auth.currentUser?.phoneNumber?.substring(3)
                                    ?.let { cartAndPurchaseViewModel.purchaseProductFromCart(it) }
                                findNavController().navigate(R.id.action_walletFragment_to_confirmPaymentFragment)
                            }
                    }

                    is WalletConnectViewModel.WalletConnectEvent.WalletTransactionError -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                        binding.TransefeeringProgressBar.visibility = View.GONE
                        binding.btnSendMoney.visibility = View.VISIBLE
                    }

                    is WalletConnectViewModel.WalletConnectEvent.WalletTransactionException -> {
                        Log.d("WalletConnectFragment", "handleEvent: ${event.exception}")
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                        binding.TransefeeringProgressBar.visibility = View.GONE
                        binding.btnSendMoney.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun updateUIOnWalletCreation() {
        binding.lottieAnimationWallet.visibility = View.GONE
        binding.btnSuccess.visibility = View.GONE
        binding.tvSuccessText.visibility = View.GONE
        binding.lottieAnimationView.visibility = View.GONE
        binding.llWalletCreated.visibility = View.VISIBLE
        binding.llSendMoney.visibility = View.VISIBLE
        binding.walletNameValue.text = walletName
        binding.walletPasswordValue.text = walletPassword
        binding.walletAddressTextValue.text = walletAddress
        getBalance()
        binding.copytext.setOnClickListener {
            copyAddress(walletAddress)
        }
    }

    private fun getBalance() {
        walletConnectViewModel.createWallet(requireContext(), walletName, walletPassword)
        walletConnectViewModel.retrieveBalance()
    }

    private fun updateUIWhenWalletNotCreated() {
        binding.tvSuccessText.visibility = View.VISIBLE
        binding.lottieAnimationWallet.visibility = View.GONE
        binding.btnSuccess.visibility = View.VISIBLE
        binding.lottieAnimationView.visibility = View.VISIBLE
        binding.llWalletCreated.visibility = View.GONE
        binding.llSendMoney.visibility = View.GONE
    }

    private fun copyAddress(address: String) {
        val clipboardManager =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clipData = ClipData.newPlainText("text", address)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun transferFund() {
        val peerAddress = binding.etPeerWalletAddress.text.toString()
        val amount = binding.etPeerBalanceSend.text.toString()
        if (peerAddress.isEmpty() || amount.isEmpty() ) {
            binding.etPeerWalletAddress.error = "Address cannot be empty"
            Toast.makeText(requireContext(), "Please enter valid details", Toast.LENGTH_SHORT)
                .show()
            hideButtonAndStartProgressHide()
            return
        } else if (peerAddress == walletAddress) {
            binding.etPeerWalletAddress.error = "You can't send money to yourself"
            Toast.makeText(requireContext(), "You can't send money to yourself", Toast.LENGTH_SHORT)
                .show()
            hideButtonAndStartProgressHide()
            return
        } else if (peerAddress.length < 42) {
            binding.etPeerWalletAddress.error = "Address is less than 42 digits"
            Toast.makeText(requireContext(), "Address is less than 42 digits", Toast.LENGTH_SHORT)
                .show()
            hideButtonAndStartProgressHide()
            return
        } else if (peerAddress.length > 42) {
            binding.etPeerWalletAddress.error = "Address is more than 42 digits"
            Toast.makeText(requireContext(), "Address is more than 42 digits", Toast.LENGTH_SHORT)
                .show()
            hideButtonAndStartProgressHide()
            return
        } else if (amount.toDouble() > balance) {
            binding.etPeerBalanceSend.error = "You don't have enough balance"
            Toast.makeText(requireContext(), "You don't have enough balance", Toast.LENGTH_SHORT)
                .show()
            hideButtonAndStartProgressHide()
            return
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                walletConnectViewModel.makeTransaction(amount.toDouble(), peerAddress )
            }
            Toast.makeText(requireContext(), "Transaction Queued, Please wait...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun hideButtonAndStartProgressHide(){
        binding.btnSendMoney.visibility = View.VISIBLE
        binding.TransefeeringProgressBar.visibility = View.GONE
    }
    private fun depositFund() {
        val recipientAddress = walletAddress
        val uri = Uri.parse("ethereum:$recipientAddress")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("io.metamask")
        startActivity(intent)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            requireActivity().startActivity(intent)
        } else {
            // Metamask app is not installed, redirect user to download it
            val marketUri = Uri.parse("market://details?id=io.metamask")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            if (marketIntent.resolveActivity(requireActivity().packageManager) != null) {
                requireActivity().startActivity(marketIntent)
            } else {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=io.metamask"))
                requireActivity().startActivity(browserIntent)
            }
        }
    }


}