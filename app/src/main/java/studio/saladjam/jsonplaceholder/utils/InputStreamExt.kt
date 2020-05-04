package studio.saladjam.jsonplaceholder.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use {bufferReader -> bufferReader.readText() }
}

fun InputStream.toByteArray (): ByteArray {
    val os = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var len: Int

    try {
        while (this.read(buffer).also { len = it } != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        os.close()
        this.close()
    }

    return os.toByteArray()
}

