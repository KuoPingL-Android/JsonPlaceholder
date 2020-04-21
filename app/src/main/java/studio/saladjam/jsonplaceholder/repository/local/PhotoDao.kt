package studio.saladjam.jsonplaceholder.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto

interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<DatabasePhoto>)

    @Query("SELECT * FROM photo_table ")
    fun getPhotos(): LiveData<List<DatabasePhoto>>

    @Query("SELECT * FROM photo_table WHERE id BETWEEN :from AND :to")
    fun getPhotosBatch(from: Int, to: Int): LiveData<List<DatabasePhoto>>
}