package app.parkee.moviedb.mvp.main.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import app.parkee.moviedb.base.BaseActivity
import app.parkee.moviedb.databinding.ActivityMainBinding
import app.parkee.moviedb.mvp.detail.view.DetailActivity
import app.parkee.moviedb.mvp.favorite.view.FavoriteActivity
import app.parkee.moviedb.mvp.main.adapter.MainAdapter
import app.parkee.moviedb.mvp.main.presenter.MainPresenter
import app.parkee.moviedb.utils.Reused
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity:BaseActivity(), MainView {
    private lateinit var binding:ActivityMainBinding
    @Inject lateinit var msp:SharedPreferences
    @Inject lateinit var mPresenter:MainPresenter

    override fun onBindingView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        super.onViewReady(savedInstanceState, intent)

        setupContent()
        grabApiData("popular")
        grabApiData("top_rated")
        grabApiData("now_playing")

        binding.ivFavorite.setOnClickListener {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
    }

    override fun onSetPresenter() {
        mPresenter.setView(this)
    }

    override fun onFailInternet() {

    }

    override fun onShowLog(msg: String) {
        showLog(TAG, msg)
    }

    override fun onShowSnackBar(msg: String, type: Int) {
        showSnackBar(binding.root, msg, type)
    }

    override fun updatePopularList(listData: ArrayList<Map<String, Any>>) {
        popularData = listData
        adapter?.notifyItemChanged(0)

//        popularData.addAll(listData)
//        adapter?.notifyItemChanged(0)
//        binding.rvContent.post {
//            adapter?.notifyItemRangeInserted(adapter!!.itemCount, popularData.size)
//        }
    }

    override fun updateTopRatedList(listData: ArrayList<Map<String, Any>>) {
        topRatedData = listData
        adapter?.notifyItemChanged(1)
    }

    override fun updateNowPlayingList(listData: java.util.ArrayList<Map<String, Any>>) {
        nowplayingData = listData
        adapter?.notifyItemChanged(2)
    }

    private var adapter:MainAdapter? = null
    private fun setupContent(){
        binding.rvContent.layoutManager = GridLayoutManager(this, 1)
        adapter = MainAdapter(this)
        binding.rvContent.adapter = adapter
    }

    private fun grabApiData(mode:String){
        val param = "$mode?api_key=9771c15367dcf3649353965f56e5dea7"
        mPresenter.grabApiData(param)
    }

    private var popularData:ArrayList<Map<String, Any>> = ArrayList()
    fun getPopularData(): ArrayList<Map<String, Any>> {
        if (popularData.size == 0){
            grabApiData("popular")
        }
        return popularData
    }

    private var topRatedData:ArrayList<Map<String, Any>> = ArrayList()
    fun getTopRatedData(): ArrayList<Map<String, Any>> {
        if (topRatedData.size == 0){
            grabApiData("top_rated")
        }
        return topRatedData
    }

    private var nowplayingData:ArrayList<Map<String, Any>> = ArrayList()
    fun getNowplayingData(): ArrayList<Map<String, Any>> {
        if (nowplayingData.size == 0){
            grabApiData("now_playing")
        }
        return nowplayingData
    }

    fun showDetailPage(data: Map<String, Any>, view: View?){
        val bundle:Bundle = Reused.mapToBundle(data)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("data", bundle)
        if (view != null){
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "backdrop")
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

}