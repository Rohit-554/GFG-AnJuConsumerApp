package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import `in` .jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.ui.activity.ConsumerActivity
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.ProductListViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentProductDetailsBinding
import java.util.Date
import java.util.Locale
import kotlin.math.abs

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var binding : FragmentProductDetailsBinding
    private lateinit var productName:String
    private lateinit var productPrice:String
    private lateinit var productImageUrl:String
    private lateinit var productDescription:String
    private lateinit var productCategory:String
    private lateinit var productExpireDate:String
    private lateinit var productId:String
    private lateinit var contractAddress:String
    private lateinit var seedingDate:String
    private val productDetailViewModel: ProductListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        (activity as ConsumerActivity).hideBottomNavigation()
        requireActivity().actionBar?.hide()
        setBundleValues()
        setUpColors()
        setUpValues()
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun setBundleValues(){
        productName = arguments?.getString("productName").toString()
        productPrice = arguments?.getString("productPrice").toString()
        productImageUrl = arguments?.getString("productImgUrl").toString()
        productDescription = arguments?.getString("productDescription").toString()
        productCategory = arguments?.getString("productType").toString()
        productExpireDate = arguments?.getString("productExpire").toString()
        productId = arguments?.getString("productId").toString()
        Log.d("productidx","$productId")
        contractAddress = arguments?.getString("contractAddress").toString()
        seedingDate = arguments?.getString("seedingDate").toString()
    }

    private fun setUpColors(){
        productDetailViewModel.getImageBitmap(requireContext(),productDetailViewModel.getImageLink(productImageUrl)){bitmap ->
            if(bitmap!=null){
                Palette.from(bitmap).generate { palette ->
                    val dominantColor = palette?.getDominantColor(ContextCompat.getColor(requireContext(),
                        `in`.jadu.anjuconsumerapp.R.color.blueColor))
                    val lightVibrantColor = palette?.getLightVibrantColor(ContextCompat.getColor(requireContext(),
                        `in`.jadu.anjuconsumerapp.R.color.blueColor))
                    changeStatusBarColor(requireActivity().window, dominantColor!!)
                    (activity as ConsumerActivity).setupActionBarColor(dominantColor)
                    binding.collapsingToolbar.setBackgroundColor(dominantColor!!)
                    binding.productDetailsLayout.setBackgroundColor(dominantColor)
                    binding.ivProductImage.setImageBitmap(bitmap)
//                    binding.appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
//                        if (abs(verticalOffset) == binding.appbarLayout.totalScrollRange) {
//                            binding.toolbar.setBackgroundColor(dominantColor)
//                            binding.toolbar.visibility = View.VISIBLE
//                            binding.collapsingToolbar.title = ""
//                        } else {
//                            binding.toolbar.visibility = View.GONE
//                            binding.collapsingToolbar.title = productName.removeSurrounding("\"")
//                        }
//                    })


                }
            }
        }
    }
    private fun setUpValues(){
//        binding.collapsingToolbar.title = productName.removeSurrounding("\"")
        binding.tvProductName.text = productName.removeSurrounding("\"")
        binding.tvExpiryDate.text = productExpireDate.removeSurrounding("\"")
        binding.tvDescription.text = productDescription.removeSurrounding("\"")
        binding.tvSeedingDate.text = seedingDate.removeSurrounding("\"")
        binding.tvPriceValue.text = "₹ "+productPrice.removeSurrounding("\"")
        val time = productId.removeSurrounding("\"").toLong()
        binding.tvProductAddedDate.text = convertUnixTimeToDate(time)
        binding.tvProductAddedTime.text = convertUnixTimeToTime(time)
        binding.productIdValue.text = productId.removeSurrounding("\"")
        binding.tvPriceProduct.text = "₹ "+productPrice.removeSurrounding("\"")
    }
    private fun changeStatusBarColor(window: Window, color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
    private fun convertUnixTimeToDate(unixTime: Long): String {
        val date = Date(unixTime) // Convert Unix timestamp to milliseconds
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }
    private fun convertUnixTimeToTime(unixTime: Long): String {
        val date = Date(unixTime * 1000) // Convert Unix timestamp to milliseconds
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return timeFormat.format(date)
    }

}