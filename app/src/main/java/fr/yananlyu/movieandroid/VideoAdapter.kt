package fr.yananlyu.movieandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.yananlyu.movieandroid.model.Film
import fr.yananlyu.movieandroid.model.MovieVideo
import com.google.android.youtube.player.YouTubeThumbnailView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat.startActivity
import android.app.Activity
import android.content.Context
import com.google.android.youtube.player.YouTubeStandalonePlayer
import android.content.Intent
import android.content.res.Resources
import android.widget.Toast
import com.google.android.youtube.player.YouTubeThumbnailLoader
import androidx.core.content.ContextCompat.startActivity
import com.google.android.youtube.player.YouTubeInitializationResult


class VideoAdapter (private val itemList: ArrayList<MovieVideo>, val listener: (MovieVideo) -> Unit):
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var movieVideo: MovieVideo
    val KEY = "AIzaSyCcdd-NS-deT6IE9k8BC2eXce__z1ONbeg"
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        movieVideo = itemList[position]
        holder.textView.text = movieVideo.name
        val obj = object: YouTubeThumbnailLoader.OnThumbnailLoadedListener{
            override fun onThumbnailLoaded(p0: YouTubeThumbnailView?, p1: String?) {
                p0?.visibility = View.VISIBLE
                holder.relativeLayoutOverYouTubeThumbnailView.visibility = View.VISIBLE
            }

            override fun onThumbnailError(
                p0: YouTubeThumbnailView?,
                p1: YouTubeThumbnailLoader.ErrorReason?
            ) {
                Toast.makeText(
                    context,
                    context.getString(R.string.app_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        holder.youTubeThumbnailView.initialize(KEY, object : YouTubeThumbnailView.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubeThumbnailView?,
                p1: YouTubeThumbnailLoader?
            ) {
                p1?.setVideo(movieVideo.key)
                p1?.setOnThumbnailLoadedListener(obj)
            }

            override fun onInitializationFailure(
                p0: YouTubeThumbnailView?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(
                    context,
                    context.getString(R.string.app_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        holder.playButton.setOnClickListener(holder)
    }

    fun addList(list: ArrayList<MovieVideo>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var relativeLayoutOverYouTubeThumbnailView: RelativeLayout = view.findViewById(R.id.relativeLayout_over_youtube_thumbnail)
        var youTubeThumbnailView: YouTubeThumbnailView = view.findViewById(R.id.youtube_thumbnail)
        var playButton: ImageView = view.findViewById(R.id.btnYoutube_player) as ImageView
        var textView:TextView = view.findViewById(R.id.video_title) as TextView
        override fun onClick(p0: View?) {
            val intent = YouTubeStandalonePlayer.createVideoIntent( context as Activity, KEY, movieVideo.key)
            context.startActivity(intent)
        }
    }
}