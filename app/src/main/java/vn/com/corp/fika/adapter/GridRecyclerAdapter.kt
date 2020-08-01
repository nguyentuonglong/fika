package vn.com.corp.fika.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.com.corp.fika.databinding.GridItemBinding
import vn.com.corp.fika.model.Moment

class GridRecyclerAdapter : RecyclerView.Adapter<GridRecyclerAdapter.ViewHolder>() {

    private var listOfUrl = mutableListOf<String>()
    private var locationWhen: String? = ""

    fun setData(data: Moment?) {
        listOfUrl.clear()
        data?.images?.filterNotNull()?.let { listOfUrl.addAll(it) }
        locationWhen = data?.location
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listOfUrl[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listOfUrl.size
    }

    inner class ViewHolder(private val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context
        fun bindItems(url: String) {
            binding.tvHappy.isVisible = adapterPosition == 0
            binding.tvLocation.isVisible = adapterPosition == 0
            binding.tvWhen.isVisible = adapterPosition == 0
            if (adapterPosition == 0) {
                binding.locationData = locationWhen
                binding.executePendingBindings()
            }
            Glide.with(context).load(url).into(binding.image)
        }
    }
}