package fr.yananlyu.movieandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Film
import android.content.Context.LAYOUT_INFLATER_SERVICE
import androidx.core.content.ContextCompat.getSystemService



class FavorisAdapter(private val itemList: ArrayList<Film>, val listener: (Film) -> Unit):
    RecyclerView.Adapter<FavorisAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavorisAdapter.ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val listItemBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.list_item, parent, false)

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FavorisAdapter.ViewHolder, position: Int) {
        val film = itemList[position]
        if(film.poster_path.isNullOrEmpty()) {
            holder.image.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + film.poster_path)
                .into(holder.image);
        }
        holder.title.text = film.original_title

        val sb = StringBuilder()
        sb.append(film.vote_average).append("/10")
        holder.rating.text = sb.toString()
        holder.itemView.setOnClickListener {
            listener(film)
        }    }

    fun addList(list: ArrayList<Film>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.img)
        val title: TextView = view.findViewById(R.id.item_title)
        val rating: TextView = view.findViewById(R.id.item_rating)
    }
}