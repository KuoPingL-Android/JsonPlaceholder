package studio.saladjam.jsonplaceholder.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.*
import studio.saladjam.jsonplaceholder.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

typealias ImageLoadCompleteHandler = ((Bitmap?) -> Unit)
object ImageLoader {
    interface ImageCache {
        fun getBitmap(url: String): Bitmap?
        fun putBitmap(url: String, bitmap: Bitmap)
    }

    private const val TIMEOUT = 10000 //ms

    private val bitmapCache: ImageCache = LruBitmapCache()

//    fun loadBitmap(urlString: String, imageView: ImageView, completeHandler: ImageLoadCompleteHandler) {
//        getBitmapFromMemCache(urlString)?.also {
//            completeHandler(it)
//        } ?: run {
//            CoroutineScope(Dispatchers.Main).launch {
//                withContext(Dispatchers.IO) {
//                    val url = URL(urlString)
//                    val conn = url.openConnection() as HttpURLConnection
//                    try {
//                        conn.connectTimeout = TIMEOUT
//                        // Cannot use Dalvik as the User-Agent for via.placeholder.com
//                        conn.requestMethod = "GET"
//                        conn.setRequestProperty("User-Agent", "Dalvic")
//                        conn.setRequestProperty("Content-Type", "image/png")
//                        conn.connect()
//
//                        var inputStream: InputStream
//                        if (conn.responseCode != HttpURLConnection.HTTP_OK) {
//                            inputStream = conn.errorStream
//                        } else {
//                            BitmapFactory.Options().run {
//                                // First decode with inJustDecodeBounds=true to check dimensions
//                                inJustDecodeBounds = true
//                                inputStream = conn.inputStream
//                                val bytes = inputStream.toByteArray()
//                                inputStream.close()
//                                BitmapFactory.decodeByteArray(bytes, 0,
//                                    bytes.size, this)
//
//                                // Calculate inSampleSize
//                                inSampleSize = calculateInSampleSize(this,
//                                    imageView.width, imageView.height)
//
//                                // Decode bitmap with inSampleSize set
//                                inJustDecodeBounds = false
//                                BitmapFactory.decodeByteArray(bytes, 0,
//                                    bytes.size, this)
//                            }?.let {
//                                bitmapCache.putBitmap(urlString, it)
//                            }
//                        }
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    } finally {
//                        try {
//                            conn.disconnect()
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
//                completeHandler(getBitmapFromMemCache(urlString))
//            }
//        }
//    }

    fun loadBitmap(urlString: String, reqWidth: Int, reqHeight: Int, completeHandler: ImageLoadCompleteHandler) {
        getBitmapFromMemCache(urlString)?.also {
            completeHandler(it)
        } ?: run {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    val url = URL(urlString)
                    val conn = url.openConnection() as HttpURLConnection
                    try {
                        conn.connectTimeout = TIMEOUT
                        // Cannot use Dalvik as the User-Agent for via.placeholder.com
                        conn.requestMethod = "GET"
                        conn.setRequestProperty("User-Agent", "Dalvic")
                        conn.setRequestProperty("Content-Type", "image/png")
                        conn.connect()

                        var inputStream: InputStream
                        if (conn.responseCode != HttpURLConnection.HTTP_OK) {
                            inputStream = conn.errorStream
                        } else {
                            BitmapFactory.Options().run {
                                // First decode with inJustDecodeBounds=true to check dimensions
                                inJustDecodeBounds = true
                                inputStream = conn.inputStream
                                val bytes = inputStream.toByteArray()
                                inputStream.close()
                                BitmapFactory.decodeByteArray(bytes, 0,
                                    bytes.size, this)

                                // Calculate inSampleSize
                                inSampleSize = calculateInSampleSize(this,
                                    reqWidth, reqHeight)

                                // Decode bitmap with inSampleSize set
                                inJustDecodeBounds = false
                                BitmapFactory.decodeByteArray(bytes, 0,
                                    bytes.size, this)
                            }?.let {
                                bitmapCache.putBitmap(urlString, it)
                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        try {
                            conn.disconnect()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
                completeHandler(getBitmapFromMemCache(urlString))
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

        // In case imageView has not yet loaded properly
//        if (reqWidth == 0 || reqHeight == 0) {
//            return inSampleSize
//        }

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