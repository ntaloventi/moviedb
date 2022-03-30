package app.parkee.moviedb.mvp.detail.view

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.core.content.ContextCompat
import app.parkee.moviedb.R
import app.parkee.moviedb.base.BaseActivity
import app.parkee.moviedb.databinding.ActivityDetailBinding
import app.parkee.moviedb.mvp.favorite.model.MyFavorite
import app.parkee.moviedb.mvp.favorite.model.MyFavorite_.mId
import app.parkee.moviedb.utils.ObjectBox
import app.parkee.moviedb.utils.Reused
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

private const val TAG = "DetailActivity"

class DetailActivity : BaseActivity(), DetailView {

    private lateinit var binding: ActivityDetailBinding
    @Inject lateinit var msp: SharedPreferences

    override fun onBindingView() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

    private var backdropPath:String? = ""
    private var title:String? = ""
    private var posterPath:String? = ""
    private var releaseDate:String? = ""
    private var overview:String? = ""
    private var movieId:Int = 0
    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        super.onViewReady(savedInstanceState, intent)

        if (intent?.hasExtra("data") == true){
            val bundle: Bundle? = intent.getBundleExtra("data")
            backdropPath = bundle?.getString("backdrop_path")
            title = bundle?.getString("title")
            posterPath = bundle?.getString("poster_path")
            releaseDate = bundle?.getString("release_date")
            overview = bundle?.getString("overview")
            movieId = bundle?.getInt("id")!!

            Glide.with(this)
                .load(Reused.getBaseImageUrl(1) + backdropPath)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivBackDrop)
            binding.tvTitle.text = title
            binding.tvOverview.text = overview
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.ivShare.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/$movieId")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.ivFavorite.setOnClickListener {
//            val fm:FragmentManager = supportFragmentManager
//            val confirm:ConfirmDialog = ConfirmDialog()
//            confirm.show(fm, "confirm")
            toggleFavorite()
        }

        initializeFavorite()
    }

    private var isFavorite:Boolean = false
    private var favBox: Box<MyFavorite>? = null
    private fun initializeFavorite() {
        if (favBox == null){
            favBox = ObjectBox.boxStore.boxFor()
        }
        val data:List<MyFavorite> = favBox!!.query(mId.equal(movieId)).build().find()
        if (data.isNotEmpty()){
            binding.ivFavorite.setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.MULTIPLY)
            isFavorite = true
        }
    }

    private fun toggleFavorite(){
        if (isFavorite){
            binding.ivFavorite.setColorFilter(ContextCompat.getColor(this, R.color.gray), PorterDuff.Mode.MULTIPLY)
            isFavorite = false
            removeFromFavorite()
            onShowSnackBar("Deleted from favorite", -1)
        } else {
            binding.ivFavorite.setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.MULTIPLY)
            isFavorite = true
            addToFavorite()
            onShowSnackBar("Added to favorite", 2)
        }
    }



    private fun addToFavorite(){
        val record = MyFavorite()
        record.backdrop_path = backdropPath
        record.title = title
        record.poster_path = posterPath
        record.release_date = releaseDate
        record.overview = overview
        record.mId = movieId

        favBox?.put(record)
    }

    private fun removeFromFavorite(){
        val data:List<MyFavorite> = favBox!!.query(mId.equal(movieId)).build().find()
        favBox?.remove(data[0])
    }
}