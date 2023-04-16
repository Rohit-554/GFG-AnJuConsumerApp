package `in`.jadu.anjuconsumerapp.consumer.models.remote

import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDto
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.CartTypeDtoItem
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.ConsumerAuth
import `in`.jadu.anjuconsumerapp.consumer.models.dtos.ProductDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConsumerApiService {
    companion object{
        const val BASE_URL = "http://34.131.137.247"
    }

    @POST("api/user/auth")
    suspend fun setPhone(@Body FarmerAuth: ConsumerAuth)

    @GET("api/product")
    suspend fun getConsumerProductList():Response<ProductDto>

    @PUT("/api/product/{phoneNo}/{productId}/cart")
    suspend fun addToCart(@Path("phoneNo") phoneNo: String, @Path("productId") productId: String, @Body cartItem: CartTypeDtoItem)

    @GET("/api/product/{phoneNo}/cart")
    suspend fun getCartItems(@Path("phoneNo") phoneNo: String):Response<CartTypeDto>




}