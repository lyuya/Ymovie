package fr.yananlyu.movieandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.model.Season

class SeasonAdapter (private val itemList: ArrayList<Season>, val listener: (Season) -> Unit):
    RecyclerView.Adapter<SeasonAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var season: Season
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.season_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        season = itemList[position]
        holder.name.text = season.name
        holder.date.text = season.air_date
        Picasso.get().load("https://image.tmdb.org/t/p/original/" + season.poster_path)
            .into(holder.img);
    }

    fun addList(list: ArrayList<Season>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById(R.id.season_title) as TextView
        var img = view.findViewById(R.id.season_img) as ImageView
        var date = view.findViewById(R.id.season_date) as TextView
    }
}