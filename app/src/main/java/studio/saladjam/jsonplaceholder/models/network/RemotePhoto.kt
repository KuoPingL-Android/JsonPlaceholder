package studio.saladjam.jsonplaceholder.models.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto

@Parcelize
data class RemotePhoto(val albumId: Int,
                       val id: Int,
                       val title: String,
                       val url: String,
                       val thumbnailUrl: String): Parcelable

fun List<RemotePhoto>.asLocalModel(): List<DatabasePhoto> {
    return map {
        DatabasePhoto(
            it.id.toString(),
            it.title,
            it.url,
            it.thumbnailUrl
        )
    }
}