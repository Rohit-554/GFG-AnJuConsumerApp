package `in`.jadu.anjuconsumerapp.consumer.ui.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.databinding.ActivityConsumerBinding


@AndroidEntryPoint
class ConsumerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsumerBinding
    private lateinit var navController: NavController
    private lateinit var customDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsumerBinding.inflate(layoutInflater)
        customDialog = Dialog(this)
        setContentView(binding.root)
        //set the top nav bar names as that of navigation lables
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.title = destination.label
        }
        binding.CameraBtn.setOnClickListener {
            showDialog()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.consumerHomeFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.consumerHomeFragment)

//                    navController.popBackStack()
                    true
                }
                R.id.consumerPreviewPaymentFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.consumerPreviewPaymentFragment)
//                    navController.popBackStack()
                    true
                }
                R.id.walletFragment -> {
                    binding.loadwalletprogress.visibility = View.VISIBLE
                    findNavController(R.id.nav_host_fragment).navigate(R.id.walletFragment)
//                    navController.popBackStack()
                    true
                }
                else -> false
            }
        }
    }
    fun showProgressBar() {
        binding.loadwalletprogress.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.loadwalletprogress.visibility = View.GONE
    }

    fun hideBottomNavigation(){
        binding.bottomNavigationView.visibility = View.GONE
        binding.bottomAppBar.visibility = View.GONE
        binding.CameraBtn.visibility = View.GONE
    }

    fun showBottomNavigation(){
        binding.bottomNavigationView.visibility = View.VISIBLE
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.CameraBtn.visibility = View.VISIBLE
    }

    fun showFab(){
        binding.CameraBtn.visibility = View.VISIBLE
    }
    fun hideFab(){
        binding.CameraBtn.visibility = View.GONE
    }
    private fun showDialog() {
        customDialog.setContentView(R.layout.createcameradialog)
        val dismissGifDialog = customDialog.findViewById<ImageView>(R.id.dismissGifDialog)
        val startCameraDialog = customDialog.findViewById<Button>(R.id.gifCamera)
        val startGalleryDialog = customDialog.findViewById<Button>(R.id.ChooseFromGallery)
        startCameraDialog.setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            options.setPrompt("Scan a barcode")
            options.setCameraId(0) // Use a specific camera of the device
            options.setOrientationLocked(true)
            options.setBeepEnabled(true)
            options.setBarcodeImageEnabled(true)
            barcodeLauncher.launch(options)
            customDialog.dismiss()
        }
        customDialog.show()
        customDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        customDialog.window?.setGravity(Gravity.BOTTOM)
        dismissGifDialog.setOnClickListener {
            customDialog.dismiss()
        }
    }

    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                this,
                "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}