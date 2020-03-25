package fr.yananlyu.movieandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Film

class RecyclerViewAdapter(private val itemList: ArrayList<Film>, val listener: (Film) -> Unit): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val feature = itemList[position]
        println("img" + feature.poster_path)
        Picasso.get().load("https://image.tmdb.org/t/p/original/" + feature.poster_path).into(holder.image);
        holder.itemView.setOnClickListener {
            listener(feature)
        }
    }

    fun addFeatureList(list: ArrayList<Film>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

       inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
           val image: ImageView = view.findViewById(R.id.img)
       }

}
