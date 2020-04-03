package fr.yananlyu.movieandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.model.Item

class SearchResultsAdapter (private val itemList: ArrayList<Item>, val listener: (Item) -> Unit) :
    RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_results_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun clearList() {
        itemList.clear()
    }
    fun addList(list: ArrayList<Item>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        if(item.media_type == "movie") {
            if(item.poster_path.isNullOrEmpty()) {
                holder.image.setImageResource(R.drawable.default_placeholder)
            } else {
                Picasso.get().load("https://image.tmdb.org/t/p/original/" + item.poster_path)
                    .into(holder.image);
            }
            holder.title.text = item.original_title
            holder.year.text = item.release_date
            holder.overview.text = item.overview
        } else if (item.media_type == "person"){
            if(item.profile_path.isNullOrEmpty()) {
                holder.image.setImageResource(R.drawable.default_placeholder)
            } else {
                Picasso.get().load("https://image.tmdb.org/t/p/original/" + item.profile_path)
                    .into(holder.image);
            }
            holder.title.text = item.name
            holder.year.visibility = View.INVISIBLE
            holder.overview.visibility = View.INVISIBLE
        } else if (item.media_type == "tv") {
            if(item.poster_path.isNullOrEmpty()) {
                holder.image.setImageResource(R.drawable.default_placeholder)
            } else {
                Picasso.get().load("https://image.tmdb.org/t/p/original/" + item.poster_path)
                    .into(holder.image);
            }
            holder.title.text = item.original_name
            holder.year.text = item.first_air_date
            holder.overview.text = item.overview
        }

        holder.itemView.setOnClickListener {
            listener(item)
        }
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.search_img)
        val title: TextView = view.findViewById(R.id.search_title)
        val year: TextView = view.findViewById(R.id.search_annee)
        val overview: TextView = view.findViewById(R.id.search_overview)
    }
}