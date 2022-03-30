package app.parkee.moviedb.mvp.main.presenter

import app.parkee.moviedb.api.ApiService
import app.parkee.moviedb.base.BasePresenter
import app.parkee.moviedb.mvp.main.view.MainView
import app.parkee.moviedb.utils.Reused
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class MainPresenter @Inject constructor():BasePresenter(), Observer<Response<ResponseBody>> {
    private lateinit var mView:MainView
    @Inject lateinit var apiService: ApiService

    fun setView(view:MainView){
        mView = view
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: Response<ResponseBody>) {
        onHandleResponse(t)
    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }

    // request fun
    fun grabApiData(param: String) {
        val response: Observable<Response<ResponseBody>> = apiService.onGetAction(param)
        mSubscribe(response, this)
    }

    // handle response
    private fun onHandleResponse(response: Response<ResponseBody>?){
        if (response != null) {
            val urlRequest:String = response.raw().request.url.toString()
            val rawBody:String = response.body()?.string() ?: ""
            when {
                urlRequest.contains("/popular") -> {
                    hadlePopularResp(rawBody)
                }
                urlRequest.contains("/top_rated") -> {
                    handleTopRated(rawBody)
                }
                urlRequest.contains("/now_playing") -> {
                    handleNowPlaying(rawBody)
                }
            }
        }
    }

    private fun hadlePopularResp(rawString: String){
        try {
            val joResp = JSONObject(rawString)
            //val page:Int = joResp.getInt("page")
            val results:JSONArray = joResp.getJSONArray("results")
            //val total_pages:Int = joResp.getInt("total_pages")
            //val total_results:Int = joResp.getInt("total_results")

            val listData:ArrayList<Map<String, Any>> = ArrayList()
            for (i in 0 until results.length()){
                val jsItem:JSONObject = results.getJSONObject(i)
                val item:Map<String, Any>? = Reused.jsonObjectToMapObj(jsItem)
                if (item != null) {
                    listData.add(item)
                }
            }
            mView.updatePopularList(listData)
        } catch (e: JSONException){
            e.printStackTrace()
        }
    }

    private fun handleTopRated(rawString: String){
        val joResp = JSONObject(rawString)
        val results:JSONArray = joResp.getJSONArray("results")

        val listData:ArrayList<Map<String, Any>> = ArrayList()
        for (i in 0 until results.length()){
            val jsItem:JSONObject = results.getJSONObject(i)
            val item:Map<String, Any>? = Reused.jsonObjectToMapObj(jsItem)
            if (item != null) {
                listData.add(item)
            }
        }
        mView.updateTopRatedList(listData)
    }

    private fun handleNowPlaying(rawString: String){
        val joResp = JSONObject(rawString)
        val results:JSONArray = joResp.getJSONArray("results")

        val listData:ArrayList<Map<String, Any>> = ArrayList()
        for (i in 0 until results.length()){
            val jsItem:JSONObject = results.getJSONObject(i)
            val item:Map<String, Any>? = Reused.jsonObjectToMapObj(jsItem)
            if (item != null) {
                listData.add(item)
            }
        }
        mView.updateNowPlayingList(listData)
    }

}