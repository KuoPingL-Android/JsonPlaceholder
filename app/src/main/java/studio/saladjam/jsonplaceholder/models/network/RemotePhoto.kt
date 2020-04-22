package studio.saladjam.jsonplaceholder.models.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import org.json.JSONObject
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto

@Parcelize
data class RemotePhoto(val albumId: Int,
                       val id: Int,
                       val title: String,
                       val url: String,
                       val thumbnailUrl: String): Parcelable {
    enum class KEYS(val value: String) {
        ALBUM_ID("albumId"),
        ID("id"),
        TITLE("title"),
        URL("url"),
        THUMBNAIL_URL("thumbnailUrl")
    }
}

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

fun JSONArray.toRemotePhotos(): List<RemotePhoto> {
    return List(length()) {
        val obj = getJSONObject(it)
        RemotePhoto(
            albumId = obj.optInt(RemotePhoto.KEYS.ALBUM_ID.value),
            id = obj.optInt(RemotePhoto.KEYS.ID.value),
            title = obj.optString(RemotePhoto.KEYS.TITLE.value),
            url = obj.optString(RemotePhoto.KEYS.URL.value),
            thumbnailUrl = obj.optString(RemotePhoto.KEYS.THUMBNAIL_URL.value)
        )
    }
}