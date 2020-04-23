package studio.saladjam.jsonplaceholder.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.*
import studio.saladjam.jsonplaceholder.R
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object ImageLoader {
    interface ImageCache {
        fun getBitmap(url: String): Bitmap?
        fun putBitmap(url: String, bitmap: Bitmap)
    }

    private val bitmapCache: ImageCache = LruBitmapCache()

    fun loadBitmap(urlString: String, imageView: ImageView) {
        getBitmapFromMemCache(urlString)?.also {
            imageView.setImageBitmap(it)
        } ?: run {
            CoroutineScope(Dispatchers.Main).launch {
                imageView.setImageResource(R.drawable.image_placeholder)
                withContext(Dispatchers.IO) {
                    val url = URL(urlString)
                    val conn = url.openConnection() as HttpsURLConnection
                    println("conn created: ${conn}  ===== $urlString")
                    conn.connect()
                    println("conn connect")
                    val inputStream = conn.inputStream
                    println("INPUT STREAM DONE")
                    BitmapFactory.Options().run {
                        // First decode with inJustDecodeBounds=true to check dimensions
                        inJustDecodeBounds = true
                        BitmapFactory.decodeStream(inputStream)

                        // Calculate inSampleSize
                        inSampleSize = calculateInSampleSize(this,
                            imageView.width, imageView.height)

                        // Decode bitmap with inSampleSize set
                        inJustDecodeBounds = false

                        BitmapFactory.decodeStream(inputStream, null, this)
                    }?.let {
                        println("SAVED ========= $urlString")
                        bitmapCache.putBitmap(urlString, it)
                    }
                }
                getBitmapFromMemCache(urlString)?.let {
                    println("PLACED ========== $urlString")
                    imageView.setImageBitmap(it)
                }
                println("DONE ======== $urlString")
            }
        }
    }

    private fun getBitmapFromMemCache(key: String): Bitmap? {
        return bitmapCache.getBitmap(key)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}