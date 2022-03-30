package app.parkee.moviedb.mvp.favorite.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class MyFavorite (
    @Id
    var id: Long = 0,
    var backdrop_path: String? = null,
    var title: String? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    var overview: String? = null,
    var mId:Int? = null
)
