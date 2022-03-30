package app.parkee.moviedb.mvp.main.view

import app.parkee.moviedb.base.BaseView
import java.util.ArrayList

interface MainView: BaseView {
    fun updatePopularList(listData: ArrayList<Map<String, Any>>)
    fun updateTopRatedList(listData: ArrayList<Map<String, Any>>)
    fun updateNowPlayingList(listData: ArrayList<Map<String, Any>>)
}