package app.parkee.moviedb.base

interface BaseView {
    fun onFailInternet()
    fun onShowLog(msg: String)
    fun onShowSnackBar(msg: String, type: Int)
}