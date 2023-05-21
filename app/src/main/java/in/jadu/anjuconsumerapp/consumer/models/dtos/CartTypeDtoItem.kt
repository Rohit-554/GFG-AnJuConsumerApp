package `in`.jadu.anjuconsumerapp.consumer.models.dtos

data class CartTypeDtoItem(
    val _id: String,
    val price: String,
    val productExpire: String,
    val productImageUrl: String,
    val productName: String,
    val productType: String,
    val quantity: String,
    val web3Id:String,
    val contractAddress:String,
)