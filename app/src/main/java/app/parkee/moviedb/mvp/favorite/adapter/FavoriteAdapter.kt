package app.parkee.moviedb.mvp.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.parkee.moviedb.R
import app.parkee.moviedb.mvp.favorite.model.MyFavorite
import app.parkee.moviedb.utils.Reused
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FavoriteAdapter(context: Context, data:List<MyFavorite>, listener:FavoriteListener):RecyclerView.Adapter<FavoriteAdapter.VH>() {
    private val mContext:Context = context
    private val mData:List<MyFavorite> = data
    private val mInflater:LayoutInflater = LayoutInflater.from(context)
    private val mListener:FavoriteListener = listener

    class VH(itemView:View):RecyclerView.ViewHolder(itemView) {
        val ivPoster:ImageView = itemView.findViewById(R.id.ivPoster)
        val tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate:TextView = itemView.findViewById(R.id.tvDate)
        val tvOverview:TextView = itemView.findViewById(R.id.tvOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view:View = mInflater.inflate(R.layout.item_favorite, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:MyFavorite = mData[position]

        Glide.with(mContext)
            .load(Reused.getBaseImageUrl() + item.poster_path)
            .transform(CenterCrop(), RoundedCorners(20))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivPoster)

        holder.tvTitle.text = item.title

        holder.tvDate.text = item.release_date?.let { Reused.getVisibleDate(it) }

        holder.tvOverview.text = item.overview

        holder.itemView.setOnClickListener {
            mListener.onFavoriteClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    interface FavoriteListener {
        fun onFavoriteClicked(data: MyFavorite)
    }
}