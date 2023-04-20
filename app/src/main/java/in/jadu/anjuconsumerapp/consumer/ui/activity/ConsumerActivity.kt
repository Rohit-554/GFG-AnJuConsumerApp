package `in`.jadu.anjuconsumerapp.consumer.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.databinding.ActivityConsumerBinding

@AndroidEntryPoint
class ConsumerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsumerBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsumerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set the top nav bar names as that of navigation lables
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.title = destination.label
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



}