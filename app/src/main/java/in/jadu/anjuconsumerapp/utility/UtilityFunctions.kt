package `in`.jadu.anjuconsumerapp.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object UtilityFunctions {
    fun getImageBitmap(context: Context, imgUrl: String, callback: (Bitmap?) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(imgUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Optional: handle when the resource is cleared
                }
            })
    }
    fun increaseColorStrength(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[1] *= 2f  // Reduce saturation by a factor of 0.7 (70%)
        return Color.HSVToColor(hsv)
    }
}