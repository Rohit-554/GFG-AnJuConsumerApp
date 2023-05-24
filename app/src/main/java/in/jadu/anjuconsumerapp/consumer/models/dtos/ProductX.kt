package `in`.jadu.anjuconsumerapp.consumer.models.dtos

data class ProductX(
    val _id: String,
    val product: ProductXX,
    val quantity: String,
    val sellerId: String,
    val status:String = "In Cart",
    )