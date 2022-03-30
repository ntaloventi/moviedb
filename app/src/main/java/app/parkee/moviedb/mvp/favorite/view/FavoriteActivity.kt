package app.parkee.moviedb.mvp.favorite.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import app.parkee.moviedb.base.BaseActivity
import app.parkee.moviedb.databinding.ActivityFavoriteBinding
import app.parkee.moviedb.mvp.detail.view.DetailActivity
import app.parkee.moviedb.mvp.favorite.adapter.FavoriteAdapter
import app.parkee.moviedb.mvp.favorite.model.MyFavorite
import app.parkee.moviedb.utils.ObjectBox
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

private const val TAG = "FavoriteActivity"

class FavoriteActivity : BaseActivity(), FavoriteView, FavoriteAdapter.FavoriteListener {

    private lateinit var binding: ActivityFavoriteBinding
    @Inject lateinit var msp: SharedPreferences

    override fun onBindingView() {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSetPresenter() {

    }

    override fun onFailInternet() {

    }

    override fun onShowLog(msg: String) {
        showLog(TAG, msg)
    }

    override fun onShowSnackBar(msg: String, type: Int) {
        showSnackBar(binding.root, msg, type)
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        super.onViewReady(savedInstanceState, intent)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavoriteList()
    }

    private fun showFavoriteList(){
        val favBox: Box<MyFavorite> = ObjectBox.boxStore.boxFor()
        val data:List<MyFavorite> = favBox.all
        binding.rvFavorite.layoutManager = GridLayoutManager(this, 1)
        val adapter = FavoriteAdapter(this, data, this)
        binding.rvFavorite.adapter = adapter
    }

    override fun onFavoriteClicked(data: MyFavorite) {
        val bundle = Bundle()
        bundle.putString("backdrop_path", data.backdrop_path)
        bundle.putString("title", data.title)
        bundle.putString("poster_path", data.poster_path)
        bundle.putString("release_date", data.release_date)
        bundle.putString("overview", data.overview)
        bundle.putInt("id", data.mId!!)

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("data", bundle)
        startActivity(intent)
    }
}