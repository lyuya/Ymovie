package fr.yananlyu.movieandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Cast

class ActorsAdapter (private val itemList: ArrayList<Cast>, val listener: (Cast) -> Unit):
    RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ActorsAdapter.ViewHolder, position: Int) {
        val cast = itemList[position]
        if(cast.profile_path.isNullOrEmpty()) {
            holder.image.setImageResource(R.drawable.default_placeholder)
        } else {
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + cast.profile_path)
                .into(holder.image);
        }
        holder.name.text = cast.name
/*        holder.itemView.setOnClickListener {
            listener(cast)
        } */   }

    fun addList(list: ArrayList<Cast>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.img_person_item)
        val name: TextView = view.findViewById(R.id.text_person_item)
    }
}