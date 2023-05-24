package `in`.jadu.anjuconsumerapp.consumer.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.databinding.ActivityProductDetailsBinding

class ProductDetails : AppCompatActivity() {
    private lateinit var binding:ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("barcode")
//            binding.tvProductId.text = value
        }
    }
}