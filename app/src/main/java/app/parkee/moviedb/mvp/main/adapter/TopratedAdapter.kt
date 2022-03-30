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

class TopratedAdapter(context:Context, data:ArrayList<Map<String, Any>>, listener:TopratedListener): RecyclerView.Adapter<TopratedAdapter.VH>() {

    private val mContext:Context = context
    private val mData:ArrayList<Map<String, Any>> = data
    private val mInflater:LayoutInflater = LayoutInflater.from(context)
    private val mListener:TopratedListener = listener

    class VH(itemView:View):RecyclerView.ViewHolder(itemView) {
        val ivTopRated:ImageView = itemView.findViewById(R.id.ivTopRated)
        val tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate:TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view:View = mInflater.inflate(R.layout.item_toprated, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:Map<String, Any> = mData[position]
        val posterPath:String = item["poster_path"] as String
        val title:String = item["title"] as String
        val releaseDate:String = item["release_date"] as String

        Glide.with(mContext)
            .load(Reused.getBaseImageUrl() + posterPath)
            .transform(CenterCrop(), RoundedCorners(20))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivTopRated)

        holder.tvTitle.text = title

        holder.tvDate.text = Reused.getVisibleDate(releaseDate)

        holder.itemView.setOnClickListener {
            mListener.onTopratedClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    interface TopratedListener {
        fun onTopratedClicked(data: Map<String, Any>)
    }
}