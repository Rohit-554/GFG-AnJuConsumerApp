package `in`.jadu.anjuconsumerapp.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.jadu.anjuconsumerapp.consumer.commonuis.viewmodels.PhoneVerificationViewModel
import `in`.jadu.anjuconsumerapp.consumer.models.remote.ConsumerApiService
import `in`.jadu.anjuconsumerapp.consumer.models.repository.ConsumerRepository
import `in`.jadu.anjuconsumerapp.kvstorage.KvStorage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    fun kvStorage(application: Application):KvStorage{
        return KvStorage(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideFarmerApiService():ConsumerApiService = Retrofit.Builder().baseUrl(ConsumerApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ConsumerApiService::class.java)


}