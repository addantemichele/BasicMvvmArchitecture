package com.example.basicmvvmarchitecture.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.basicmvvmarchitecture.R
import com.example.basicmvvmarchitecture.databinding.ListItemBinding
import com.example.basicmvvmarchitecture.model.AnimeModel

class AnimeRecyclerViewAdapter (private val clickListener: (AnimeModel) -> Unit) : RecyclerView.Adapter<AnimeViewHolder>() {

    private val animeList = ArrayList<AnimeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)

        return AnimeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position], clickListener)
    }

    fun setList(animeList: List<AnimeModel>) {
        this.animeList.clear()
        this.animeList.addAll(animeList)
    }

}

class AnimeViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(animeModel: AnimeModel,clickListener: (AnimeModel) -> Unit){
        binding.animeTitleTextview.text = animeModel.title
        binding.animeEpisodesTextview.text = animeModel.numberOfEpisodes.toString()
        binding.listItemLayout.setOnClickListener {
            clickListener(animeModel)
        }
    }
}