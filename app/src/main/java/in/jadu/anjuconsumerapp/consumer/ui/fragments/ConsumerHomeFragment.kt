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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.consumer.adapters.ProductListAdapter
import `in`.jadu.anjuconsumerapp.consumer.commonuis.viewmodels.PhoneVerificationViewModel
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.Product
import `in`.jadu.anjuconsumerapp.consumer.ui.activity.ConsumerActivity
import `in`.jadu.anjuconsumerapp.consumer.viewmodels.GetITemListViewModel
import `in`.jadu.anjuconsumerapp.databinding.FragmentConsumerHomeBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConsumerHomeFragment : Fragment(),androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var binding : FragmentConsumerHomeBinding
    private val phoneVerificationViewModel: PhoneVerificationViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val getITemListViewModel: GetITemListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductListAdapter
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()
        createUserOnServer()
        (activity as ConsumerActivity).showBottomNavigation()
        binding = FragmentConsumerHomeBinding.inflate(inflater, container, false)
        bundle = bundleOf()
        recyclerView = binding.rvListOfProducts
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(),2, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        adapter = ProductListAdapter()
        //call the main event
        lifecycleScope.launch {
            getITemListViewModel.mainEvent.collect() { event ->
                when (event) {
                    is GetITemListViewModel.MainEvent.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("TAG", "onCreateView: ")
                    }
                }
            }
        }

        getITemListViewModel.getAllItemList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                Toast.makeText(requireContext(), "No Products Available Now", Toast.LENGTH_SHORT).show()
            }else{

                adapter.itemTypes = it
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        adapter.setOnItemClickListener(object : ProductListAdapter.OnItemClickListener {
            override fun onItemButtonClicked(product: Product) {
                Log.d("rohitss", "onItemClick: "+product)
                getITemListViewModel.addToCart(auth.currentUser?.phoneNumber?.substring(3)!!,product._id,product)
                Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
            }

            override fun onItemClicked(product: Product) {
                Log.d("rohitss", "onItemClickItem: "+product)
                bundle = bundleOf(
                    "productName" to product.productName,
                    "productDescription" to product.description,
                    "productExpire" to product.productExpire,
                    "productImgUrl" to product.productImageUrl,
                    "seedingDate" to product.productPacked,
                    "productType" to product.productType,
                    "productId" to product.web3Id,
                    "contractAddress" to product.contractAddress,
                    "productPrice" to product.productPrice,
                )
                findNavController().navigate(R.id.action_consumerHomeFragment_to_productDetailsFragment,bundle)
            }
        })
        return binding.root
    }

    private fun createUserOnServer() {
        auth.currentUser?.phoneNumber?.substring(3)?.let { phoneVerificationViewModel.uploadPhoneNo(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeBackButton()
        setupMenu()
    }

    private fun removeBackButton(){
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menuitems, menu)
                val searchItem = menu.findItem(R.id.menuSearch)
                val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
                searchView.setOnQueryTextListener(this@ConsumerHomeFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.logout -> {
                        Toast.makeText(requireContext(), "Logging Out", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        findNavController().navigate(R.id.action_consumerHomeFragment_to_selectLanguageFragment)
                    }
                }

                return true
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }


    override fun onQueryTextChange(p0: String?): Boolean {
        filterListAccordingToKeyword(p0)
        return true
    }


    private fun filterListAccordingToKeyword(p0: String?) {
        getITemListViewModel.getAllItemList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                Toast.makeText(requireContext(), "No Item Found", Toast.LENGTH_SHORT).show()
            }else{
                //check if some letters also match then show the product
                val filteredList = it.filter { product ->
                    val queryWords = p0.toString().trim().split("\\s+".toRegex())
                    queryWords.any { queryWord ->
                        product.productName.contains(queryWord, true)
                    }
                }
                adapter.itemTypes = filteredList
                adapter.notifyDataSetChanged()
            }

        }
    }
}