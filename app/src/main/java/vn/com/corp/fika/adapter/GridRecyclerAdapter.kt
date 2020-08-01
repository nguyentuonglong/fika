package vn.com.corp.fika.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.com.corp.fika.databinding.GridItemBinding

class GridRecyclerAdapter : RecyclerView.Adapter<GridRecyclerAdapter.ViewHolder>() {

    private var listOfUrl = mutableListOf<String>()

    fun setData(data: List<String>) {
        listOfUrl.clear()
        listOfUrl.addAll(data)
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

    class ViewHolder(private val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context
        fun bindItems(url: String) {
            Glide.with(context).load(url).into(binding.image)
        }
    }
}