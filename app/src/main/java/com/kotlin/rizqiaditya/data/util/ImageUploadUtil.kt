package com.kotlin.rizqiaditya.data.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.sqrt

private const val DEFAULT_MAX_UPLOAD_BYTES: Long = 1_000_000L

object ImageUploadUtil {
    fun ensureUnderMax(file: File, maxBytes: Long = DEFAULT_MAX_UPLOAD_BYTES): File? {
        return try {
            if (file.length() <= maxBytes) return file
            compressToMaxSize(file, maxBytes)
        } catch (e: Exception) {
            Log.w("ImageUploadUtil", "ensureUnderMax error: ${e.message}")
            null
        }
    }

    private fun compressToMaxSize(input: File, maxBytes: Long): File? {
        try {
            val originalSize = input.length()
            if (originalSize <= maxBytes) return input

            val ratio = originalSize.toDouble() / maxBytes.toDouble()
            var sample = max(1, sqrt(ratio).toInt())
            var inSampleSize = 1
            while (inSampleSize * 2 <= sample) inSampleSize *= 2

            val options = BitmapFactory.Options().apply { inJustDecodeBounds = false; this.inSampleSize = inSampleSize }
            var origBitmap = BitmapFactory.decodeFile(input.absolutePath, options) ?: return null

            var bitmap: Bitmap = origBitmap
            var quality = 90
            val minQuality = 30
            val minDimension = 100
            var attempt = 0

            while (true) {
                attempt++
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
                val bytes = baos.toByteArray()

                if (bytes.size <= maxBytes) {
                    val parent = input.parentFile ?: input
                    val outFile = File(parent, "compressed_${System.currentTimeMillis()}.jpg")
                    FileOutputStream(outFile).use { it.write(bytes) }
                    if (bitmap !== origBitmap && !bitmap.isRecycled) bitmap.recycle()
                    if (!origBitmap.isRecycled) origBitmap.recycle()
                    return outFile
                }

                if (quality > minQuality) {
                    quality = (quality - 10).coerceAtLeast(minQuality)
                    continue
                }

                if (bitmap.width <= minDimension || bitmap.height <= minDimension) {
                    val parent = input.parentFile ?: input
                    val outFile = File(parent, "compressed_${System.currentTimeMillis()}.jpg")
                    FileOutputStream(outFile).use { it.write(bytes) }
                    try {
                        val webpOut = File(parent, "compressed_webp_${System.currentTimeMillis()}.jpg")
                        val wb = ByteArrayOutputStream()
                        val webpFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) Bitmap.CompressFormat.WEBP_LOSSY else Bitmap.CompressFormat.WEBP
                        bitmap.compress(webpFormat, 80, wb)
                        val webpBytes = wb.toByteArray()
                        if (webpBytes.size < bytes.size) {
                            FileOutputStream(webpOut).use { it.write(webpBytes) }
                            if (bitmap !== origBitmap && !bitmap.isRecycled) bitmap.recycle()
                            if (!origBitmap.isRecycled) origBitmap.recycle()
                            return webpOut
                        } else {
                            try { if (webpOut.exists()) webpOut.delete() } catch (_: Exception) {}
                        }
                    } catch (_: Exception) {}
                    return outFile
                }

                val newW = (bitmap.width * 0.9f).toInt().coerceAtLeast(minDimension)
                val newH = (bitmap.height * 0.9f).toInt().coerceAtLeast(minDimension)
                val scaled = Bitmap.createScaledBitmap(bitmap, newW, newH, true)
                if (bitmap !== origBitmap && !bitmap.isRecycled) bitmap.recycle()
                bitmap = scaled
                quality = 90
            }
        } catch (e: Exception) {
            Log.w("ImageUploadUtil", "compressToMaxSize error: ${e.message}")
            return null
        }
    }

    fun detectImageMime(file: File): String? {
        try {
            val header = ByteArray(12)
            FileInputStream(file).use { fis ->
                val read = fis.read(header)
                if (read <= 0) return null
            }

            if (header.size >= 3 && header[0] == 0xFF.toByte() && header[1] == 0xD8.toByte() && header[2] == 0xFF.toByte()) {
                return "image/jpeg"
            }

            if (header.size >= 4 && header[0] == 0x89.toByte() && header[1] == 0x50.toByte() && header[2] == 0x4E.toByte() && header[3] == 0x47.toByte()) {
                return "image/png"
            }

            if (header.size >= 4 && header[0] == 0x47.toByte() && header[1] == 0x49.toByte() && header[2] == 0x46.toByte() && header[3] == 0x38.toByte()) {
                return "image/gif"
            }

            if (header.size >= 12) {
                val riff = String(header, 0, 4, Charsets.US_ASCII)
                val webp = String(header, 8, 4, Charsets.US_ASCII)
                if (riff == "RIFF" && webp == "WEBP") {
                    return "image/webp"
                }
            }

            try {
                val asAscii = String(header, Charsets.US_ASCII)
                if (asAscii.contains("ftyp") && (asAscii.contains("heic") || asAscii.contains("heix") || asAscii.contains("mif1") || asAscii.contains("msf1"))) {
                    return "image/heic"
                }
            } catch (_: Throwable) {}

            val extension = file.name.substringAfterLast('.', "jpg").lowercase()
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        } catch (e: Exception) {
            Log.w("ImageUploadUtil", "detectImageMime error: ${e.message}")
            return null
        }
    }

    fun recompressToJpegInSameDir(input: File): File? {
        return try {
            val bitmap = BitmapFactory.decodeFile(input.absolutePath) ?: return null
            val parent = input.parentFile ?: input
            val outFile = File(parent, "recompressed_${System.currentTimeMillis()}.jpg")
            FileOutputStream(outFile).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
                fos.flush()
            }
            outFile
        } catch (e: Exception) {
            Log.w("ImageUploadUtil", "recompressToJpegInSameDir error: ${e.message}")
            null
        }
    }

    fun buildMultipartPart(name: String, file: File): MultipartBody.Part {
        val extension = file.name.substringAfterLast('.', "jpg").lowercase()
        val mimeFromExt = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        val detected = detectImageMime(file)
        val detectedMime = detected ?: mimeFromExt ?: "image/jpeg"
        val mime = if (detectedMime.startsWith("image/")) detectedMime else "image/jpeg"
        return MultipartBody.Part.createFormData(
            name = name,
            filename = file.name,
            body = file.asRequestBody(mime.toMediaType())
        )
    }
}
