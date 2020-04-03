package fr.yananlyu.movieandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.model.MoviePerson

class ActorsAdapter (private val itemList: ArrayList<MoviePerson>, val listener: (MoviePerson) -> Unit):
    RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = itemList[position]
        if(person.profile_path.isNullOrEmpty()) {
            holder.image.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + person.profile_path)
                .into(holder.image);
        }
        holder.name.text = person.name
        holder.itemView.setOnClickListener {
            listener(person)
        }
    }

    fun addList(list: ArrayList<MoviePerson>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }
    fun clearList() {
        itemList.clear()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.img_person_item)
        val name: TextView = view.findViewById(R.id.text_person_item)
    }
}