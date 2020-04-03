package fr.yananlyu.movieandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.model.MovieImage

class SlideAdapter(private val context: Context, private var images:ArrayList<MovieImage>): PagerAdapter() {
    lateinit var layoutinflater: LayoutInflater

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutinflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutinflater.inflate(R.layout.fragment_slideshow, container,false)
        val image: ImageView = v.findViewById<ImageView>(R.id.slideshowImage)
        if (images.isNullOrEmpty()) {
            image.setImageResource(R.drawable.backdrop)
        }else{
            Picasso.get().load("https://image.tmdb.org/t/p/original" + images[position].file_path).into(image)
        }
        val vp = container as ViewPager
        vp.addView(v, 0)
        return v
    }
    fun addImages(list: ArrayList<MovieImage>) {
        images.clear()
        images.addAll(list)
        notifyDataSetChanged()
    }
}