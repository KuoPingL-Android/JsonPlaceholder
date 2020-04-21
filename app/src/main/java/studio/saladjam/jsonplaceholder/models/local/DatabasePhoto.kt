package studio.saladjam.jsonplaceholder.models.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "photo_table")
data class DatabasePhoto(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String): Parcelable
