package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import dagger.hilt.android.AndroidEntryPoint
import `in` .jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.ui.activity.ConsumerActivity
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.CartAndPurchaseViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentOrderedProductDetailsBinding
import java.util.function.Consumer

@AndroidEntryPoint
class OrderedProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentOrderedProductDetailsBinding
    private var position:Int=0
    private val cartAndPurchaseViewModel: CartAndPurchaseViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentOrderedProductDetailsBinding.inflate(inflater,container,false)
        setupView()
        cartAndPurchaseViewModel.getOrderedProducts.observe(viewLifecycleOwner){

        }
        return binding.root
    }

    fun setupView(){
        position = arguments?.getInt("position")!!
        cartAndPurchaseViewModel.getOrderedProducts.observe(requireActivity()){plist->
            binding.tvProductName.text = plist.orders.products[position].product.productName
            binding.tvExpiryDate.text = plist.orders.products[position].product.productExpire
            binding.tvDescription.text = plist.orders.products[position].product.description
            binding.productIdValue.text = plist.orders.products[position].product.web3Id.toString()
            binding.tvPriceValue.text = plist.orders.products[position].product.productPrice.toString()
            binding.tvOrderIdValue.text = plist.orders._id
            binding.tvOrderedByValue.text = plist.orders.userName
            binding.tvDeliveryLocationValue.text = plist.orders.userAddress
            binding.tvPaymentStatusValue.text = "In Transit"
            cartAndPurchaseViewModel.getImageBitmap(requireContext(),cartAndPurchaseViewModel.getImageLink(plist.orders.products[position].product.productImageUrl)){bitmap->
                if(bitmap!=null){
                    Palette.from(bitmap).generate { palette ->
                        val dominantColor = palette?.getDominantColor(
                            ContextCompat.getColor(requireContext(),
                                `in`.jadu.anjuconsumerapp.R.color.blueColor))
                        val lightVibrantColor = palette?.getLightVibrantColor(
                            ContextCompat.getColor(requireContext(),
                                `in`.jadu.anjuconsumerapp.R.color.blueColor))
                        changeStatusBarColor(requireActivity().window, dominantColor!!)
                        (activity as ConsumerActivity).setupActionBarColor(dominantColor)
                        binding.collapsingToolbar.setBackgroundColor(dominantColor!!)
                        binding.productDetailsLayout.setBackgroundColor(dominantColor)
                        binding.ivProductImage.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
    private fun changeStatusBarColor(window: Window, color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as ConsumerActivity).setupActionBarColor(ContextCompat.getColor(requireContext(),
            `in`.jadu.anjuconsumerapp.R.color.blueColor))
        changeStatusBarColor(requireActivity().window, ContextCompat.getColor(requireContext(),
            `in`.jadu.anjuconsumerapp.R.color.blueColor))
    }

}