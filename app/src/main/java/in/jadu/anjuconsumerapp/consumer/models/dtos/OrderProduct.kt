package `in`.jadu.anjuconsumerapp.consumer.models.dtos

data class OrderProduct(
    val userAddress: String,
    val userName:String,
    val userPhoneNo:String,
    val contractAddress:String,
    val web3Id:String,
    val paymentAmount:String,
    val status:String,
)