package fr.yananlyu.movieandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Film

class FavorisAdapter(private val itemList: ArrayList<Film>, val listener: (Film) -> Unit):
    RecyclerView.Adapter<FavorisAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavorisAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favoris_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FavorisAdapter.ViewHolder, position: Int) {
        val film = itemList[position]
        if(film.poster_path.isNullOrEmpty()) {
            println("film.poster_path"+film.poster_path)
            holder.image.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.poster_path)
                .into(holder.image);
        }
        holder.title.text = film.original_title
        holder.itemView.setOnClickListener {
            listener(film)
        }    }

    fun addList(list: ArrayList<Film>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.img_favoris_item)
        val title: TextView = view.findViewById(R.id.text_favoris_item)
    }
}