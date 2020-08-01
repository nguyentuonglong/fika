package vn.com.corp.fika.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

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

    fun convertToDob(milliSeconds: Long): String? {
        val dateFormat = "MMM yyyy"
        val formatter = SimpleDateFormat(dateFormat, Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun getYearOld(milliSeconds: Long): Int? {
        val c1: Calendar = Calendar.getInstance()
        c1.timeInMillis = milliSeconds
        val y1 = c1.get(Calendar.YEAR)

        val c2 = Calendar.getInstance()
        val y2 = c2.get(Calendar.YEAR)
        return y2 - y1
    }
}