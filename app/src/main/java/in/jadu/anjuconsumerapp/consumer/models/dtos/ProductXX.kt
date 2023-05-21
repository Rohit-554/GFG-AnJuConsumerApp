package `in`.jadu.anjuconsumerapp.consumer.models.dtos

import `in`.jadu.anjuconsumerapp.consumer.models.dtos.ProductPurchased

data class ProductXX(
    val __v: Int,
    val _id: String,
    val contractAddress: String,
    val creator: String,
    val description: String,
    val productExpire: String,
    val productImageUrl: String,
    val productName: String,
    val productPacked: String,
    val productPrice: String,
    val productPurchased: List<ProductPurchased>,
    val productType: String,
    val web3Id: String
)