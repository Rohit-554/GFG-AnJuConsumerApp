package `in`.jadu.anjuconsumerapp.consumer.models.dtos



data class Orders(
    val __v: Int,
    val _id: String,
    val contractAddress: String,
    val date: String,
    val paymentAmount: String,
    val products: List<ProductX>,
    val userAddress: String,
    val userId: String,
    val userName: String,
    val userPhoneNo: String,
    val web3Id: String
)