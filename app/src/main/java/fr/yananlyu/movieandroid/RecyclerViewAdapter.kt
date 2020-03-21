package fr.yananlyu.movieandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.yananlyu.movieandroid.model.Film

class RecyclerViewAdapter(private val itemList: ArrayList<Film>, val listener: (Film) -> Unit): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*   override fun getItemCount(): Int {
           return itemList.size
       }

       fun addFeatureList(list: ArrayList<Film>) {
           itemList.clear()
           itemList.addAll(list)
           notifyDataSetChanged()
       }

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
           val view = LayoutInflater.from(parent.context)
               .inflate(R.layout.item_feature, parent, false)
           return ViewHolder(view)
       }

       override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           val feature = itemList[position]

           holder.name.text = feature.properties.name
           holder.street.text = feature.properties.street
           holder.address.text = feature.properties.postalCode.toString() + " - " + feature.properties.city
           holder.image.setImageResource(R.drawable.metro_logo)

           holder.itemView.setOnClickListener {
               listener(feature)
           }
       }*/

       inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
           /*val name: TextView = view.feature_name
           val address: TextView = view.feature_address
           val street: TextView = view.feature_street
           val image: ImageView = view.feature_type*/
       }
}
