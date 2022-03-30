package app.parkee.moviedb.mvp.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.parkee.moviedb.R
import app.parkee.moviedb.utils.Reused
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PopularAdapter(context: Context, data: ArrayList<Map<String, Any>>, listener: PopularListener): RecyclerView.Adapter<PopularAdapter.VH>() {

    private val mContext:Context = context
    private val mData:ArrayList<Map<String, Any>> = data
    private val mInflater:LayoutInflater = LayoutInflater.from(context)
    private val mListener:PopularListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view:View = mInflater.inflate(R.layout.item_popular, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:Map<String, Any> = mData[position]
        val backdropPath:String = item["backdrop_path"] as String
        val posterPath:String = item["poster_path"] as String
        val title:String = item["title"] as String
        val releaseDate:String = item["release_date"] as String

        Glide.with(mContext)
            .load(Reused.getBaseImageUrl(1) + backdropPath)
            .transform(CenterCrop(), RoundedCorners(30))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivBackdrop)

        Glide.with(mContext)
            .load(Reused.getBaseImageUrl() + posterPath)
            .transform(CenterCrop(), RoundedCorners(10))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivPoster)

        holder.tvTitle.text = title

        holder.tvDate.text = Reused.getVisibleDate(releaseDate)

        holder.itemView.setOnClickListener {
            mListener.onPopularItemClicked(item, holder.ivBackdrop)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class VH(itemView:View):RecyclerView.ViewHolder(itemView) {
        val ivBackdrop: ImageView = itemView.findViewById(R.id.ivBackdrop)
        val ivPoster:ImageView = itemView.findViewById(R.id.ivPoster)
        val tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate:TextView = itemView.findViewById(R.id.tvDate)
    }

    interface PopularListener {
        fun onPopularItemClicked(data: Map<String, Any>, view: View)
    }
}