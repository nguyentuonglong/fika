package vn.com.corp.fika.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object Utils {
    @JvmStatic
    fun displayImageOriginal(
        context: Context,
        img: ImageView,
        url: MutableList<String>,
        position: Int
    ) {
        try {
            Glide.with(context).load(url[position])
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(img)
        } catch (e: java.lang.Exception) {
            e.message
        }
    }
}