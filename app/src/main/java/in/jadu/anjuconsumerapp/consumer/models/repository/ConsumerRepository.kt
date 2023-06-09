package `in`.jadu.anjuconsumerapp.consumer.models.repository

import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.ConsumerAuth
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.OrderProduct
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.ProductOrderDetails
import `in`.jadu.anjuconsumerapp.consumer.models.remote.ConsumerApiService
import javax.inject.Inject

class ConsumerRepository @Inject constructor(private val consumerApiService: ConsumerApiService){

    suspend fun setPhone(phoneNo: String) = consumerApiService.setPhone(ConsumerAuth(phoneNo))

    suspend fun consumerProductList() = consumerApiService.getConsumerProductList()

    suspend fun addToCart(phoneNo: String, productId: String,cartItem:CartTypeDtoItem) = consumerApiService.addToCart(phoneNo, productId,cartItem)

    suspend fun getCartItems(phoneNo: String) = consumerApiService.getCartItems(phoneNo)

    suspend fun purchasedProductList(phoneNo: String,orderProduct: OrderProduct) = consumerApiService.purchasedProductList(phoneNo,orderProduct)

    suspend fun getPurchasedProductList(phoneNo: String) = consumerApiService.getPurchasedProductList(phoneNo)

    suspend fun orderProduct(phoneNo: String) = consumerApiService.orderProduct(phoneNo)
}