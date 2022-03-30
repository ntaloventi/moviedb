package app.parkee.moviedb.mvp.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import app.parkee.moviedb.R
import app.parkee.moviedb.mvp.main.view.MainActivity

class MainAdapter(context:Context):RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    PopularAdapter.PopularListener,
    TopratedAdapter.TopratedListener,
    NowplayingAdapter.NowplayListener {

    private  val mContext:Context = context
    private val mInflater:LayoutInflater = LayoutInflater.from(context)


    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view:View = mInflater.inflate(R.layout.main_popular, parent, false)
                PopularVH(view)
            }
            1 -> {
                val view:View = mInflater.inflate(R.layout.main_toprated, parent, false)
                TopRatedVH(view)
            }
            else -> {
                val view:View = mInflater.inflate(R.layout.main_nowplaying, parent, false)
                NowPlayingVH(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularVH -> {
                holder.rvPopular.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                val data:ArrayList<Map<String, Any>> = (mContext as MainActivity).getPopularData()
                val adapter = PopularAdapter(mContext, data, this)
                holder.rvPopular.adapter = adapter
                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(holder.rvPopular)
            }
            is TopRatedVH -> {
                holder.rvToprated.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                val data:ArrayList<Map<String, Any>> = (mContext as MainActivity).getTopRatedData()
                val adapter = TopratedAdapter(mContext, data, this)
                holder.rvToprated.adapter = adapter
    //            val snapHelper:LinearSnapHelper = LinearSnapHelper()
    //            snapHelper.attachToRecyclerView(holder.rvToprated)
            }
            is NowPlayingVH -> {
                holder.rvNowplaying.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                val data:ArrayList<Map<String, Any>> = (mContext as MainActivity).getNowplayingData()
                val adapter = NowplayingAdapter(mContext, data, this)
                holder.rvNowplaying.adapter = adapter
    //            val snapHelper:LinearSnapHelper = LinearSnapHelper()
    //            snapHelper.attachToRecyclerView(holder.rvNowplaying)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    class PopularVH(itemView:View):RecyclerView.ViewHolder(itemView) {
        val rvPopular:RecyclerView = itemView.findViewById(R.id.rvPopular)
    }

    class TopRatedVH(itemView:View):RecyclerView.ViewHolder(itemView) {
        val rvToprated:RecyclerView = itemView.findViewById(R.id.rvToprated)
    }

    class NowPlayingVH(itemView:View):RecyclerView.ViewHolder(itemView) {
        val rvNowplaying:RecyclerView = itemView.findViewById(R.id.rvNowplaying)
    }

    override fun onPopularItemClicked(data: Map<String, Any>, view: View) {
        (mContext as MainActivity).showDetailPage(data, view)
    }

    override fun onTopratedClicked(data: Map<String, Any>) {
        (mContext as MainActivity).showDetailPage(data, null)
    }

    override fun onNowplayClicked(data: Map<String, Any>) {
        (mContext as MainActivity).showDetailPage(data, null)
    }

}