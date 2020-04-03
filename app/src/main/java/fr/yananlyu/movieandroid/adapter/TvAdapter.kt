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
import fr.yananlyu.movieandroid.model.Tv

class TvAdapter (private val itemList: ArrayList<Tv>, val listener: (Tv) -> Unit):
    RecyclerView.Adapter<TvAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = itemList[position]
        holder.name.text = tv.original_name
        val sb = StringBuilder()
        sb.append(tv.vote_average).append("/10")
        holder.rating.text = sb.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/original/" + tv.poster_path)
            .into(holder.img);
        holder.itemView.setOnClickListener {
            println(tv.original_name)
            listener(tv)
        }
    }

    fun addList(list: ArrayList<Tv>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById(R.id.item_title) as TextView
        var img = view.findViewById(R.id.img) as ImageView
        var rating = view.findViewById(R.id.item_rating) as TextView
    }
}