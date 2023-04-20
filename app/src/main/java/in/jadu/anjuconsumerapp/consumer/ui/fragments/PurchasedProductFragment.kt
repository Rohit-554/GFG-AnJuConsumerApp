package `in`.jadu.anjuconsumerapp.consumer.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.adapters.PurchasedProductAdapter
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.CartAndPurchaseViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentPurchasedProductBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchasedProductFragment : Fragment() {
    private lateinit var binding: FragmentPurchasedProductBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var purchasedProductAdapter: PurchasedProductAdapter
    private val cartAndPurchaseViewModel: CartAndPurchaseViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchasedProductBinding.inflate(inflater, container, false)
        recyclerView = binding.rvPurchasedProduct
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        purchasedProductAdapter = PurchasedProductAdapter()
        handleEvent()

        cartAndPurchaseViewModel.getPurchasedItems.observe(viewLifecycleOwner){
            Log.d("purchaseProduct", "onCreateView: $it")
            if(it.isNullOrEmpty()){
                Toast.makeText(requireContext(), "No Products Purchased", Toast.LENGTH_SHORT).show()
            }else{
                purchasedProductAdapter.purchasedProductList = it
                recyclerView.adapter = purchasedProductAdapter
                purchasedProductAdapter.notifyDataSetChanged()
            }
        }



        // Inflate the layout for this fragment
        return binding.root
    }
    private fun handleEvent() {
        lifecycleScope.launch {
            cartAndPurchaseViewModel.mainEvent.collect { event ->
                when (event) {
                    is CartAndPurchaseViewModel.MainEvent.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("TAG", "onCreateView: ")
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.purchasedproduct_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                  R.id.action_go_to_home -> {
                      findNavController().navigate(R.id.action_purchasedProductFragment_to_consumerHomeFragment)
                  }
                }

                return true
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}