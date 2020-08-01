package vn.com.corp.fika.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import vn.com.corp.fika.FikaApp.Companion.applicationContext
import vn.com.corp.fika.R
import vn.com.corp.fika.util.Utils.displayImageOriginal


class AdapterImageSlider : PagerAdapter() {
    private val items: MutableList<String> = mutableListOf()


    override fun getCount(): Int {
        return items.size
    }

    fun setItems(items: List<String>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = applicationContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.item_slider_image, container, false)
        val imageView =
            v.findViewById<ImageView>(R.id.image)
        displayImageOriginal(
            applicationContext(),
            imageView,
            items,
            position
        )
        container.addView(v)
        return v
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as RelativeLayout)
    }
}