package studio.saladjam.jsonplaceholder.utils

import android.graphics.Bitmap
import android.util.LruCache

// https://github.com/Kotlin/kotlin-examples/blob/master/gradle/android-volley/app/src/main/java/org/example/kotlin/volley/LruBitmapCache.kt
class LruBitmapCache constructor(sizeInKB: Int = defaultLruCacheSize):
    LruCache<String, Bitmap>(sizeInKB), ImageLoader.ImageCache {

    override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount / 1024

    override fun getBitmap(url: String): Bitmap? {
        return get(url)
    }

    override fun putBitmap(url: String, bitmap: Bitmap) {
        put(url, bitmap)
    }

    companion object {
        val defaultLruCacheSize: Int
            get() {
                val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
                // Use 1/8th of the available memory for this memory cache.
                return maxMemory / 8
            }
    }
}

