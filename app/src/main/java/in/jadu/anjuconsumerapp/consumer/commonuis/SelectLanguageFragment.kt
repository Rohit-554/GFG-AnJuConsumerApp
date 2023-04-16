package `in`.jadu.anjuconsumerapp.consumer.commonuis

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R
import `in`.jadu.anjuconsumerapp.databinding.FragmentSelectLanguageBinding
import `in`.jadu.anjuconsumerapp.kvstorage.KvStorage
import java.lang.reflect.Constructor
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SelectLanguageFragment () : Fragment() {
    private lateinit var binding: FragmentSelectLanguageBinding
    @Inject lateinit var kvStorage: KvStorage
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectLanguageBinding.inflate(inflater,container,false)
        navigateViaHindi()
        navigateViaEnglish()
        auth = FirebaseAuth.getInstance()
        checkUserExist()
        changeAppLanguage()
        return binding.root
    }



    private fun navigateViaHindi(){
        binding.btnHindi.setOnClickListener {
            findNavController().navigate(R.id.action_selectLanguageFragment_to_phoneVerificationFragment)
            kvStorage.storageSetString("AppLanguage","hindi")
            changeAppLanguage()
        }
    }

    private fun navigateViaEnglish(){
        binding.btnEnglish.setOnClickListener {
            findNavController().navigate(R.id.action_selectLanguageFragment_to_phoneVerificationFragment)
            kvStorage.storageSetString("AppLanguage","english")
            changeAppLanguage()
        }
    }

    //create a function that will change the app language
    private fun changeAppLanguage(){
        val language = kvStorage.storageGetString("AppLanguage")
        if(language == "hindi"){
            //change the app language hindi
            setLocale("hi")

        }else{
            setLocale("en-us")
        }
    }

    private fun setLocale(language: String) {
        val myLocale = Locale(language)
        Locale.setDefault(myLocale)
        val configuration:Configuration = resources.configuration
        configuration.setLocale(myLocale)
        requireActivity().baseContext.resources.updateConfiguration(configuration,requireActivity().baseContext.resources.displayMetrics)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun checkUserExist(){
        val currentUser = auth.currentUser
        if(currentUser != null){
            findNavController().navigate(R.id.action_selectLanguageFragment_to_consumerHomeFragment)
        }
    }
}