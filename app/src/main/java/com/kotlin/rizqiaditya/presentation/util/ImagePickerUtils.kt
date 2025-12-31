package com.kotlin.rizqiaditya.presentation.util

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun uriToFile(uri: Uri, context: Context): File? {
    return try {
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        var originalName: String? = null
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    originalName = it.getString(nameIndex)
                }
            }
        }

        // try to get mime type from content resolver
        val mimeType = contentResolver.getType(uri)
        val extension = if (mimeType != null) {
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        } else {
            // fallback: try to guess from original name or uri path
            val fromName = originalName?.substringAfterLast('.', "")
            val fromPath = uri.path?.substringAfterLast('.', "")
            when {
                !fromName.isNullOrBlank() -> fromName
                !fromPath.isNullOrBlank() -> fromPath
                else -> null
            }
        } ?: "jpg"

        val fileName = originalName ?: "picked_image_${System.currentTimeMillis()}.$extension"
        val safeFileName = if (fileName.contains('.')) fileName else "$fileName.$extension"

        val file = File(context.cacheDir, safeFileName)
        val outputStream = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var len: Int
        var total = 0L
        if (inputStream != null) {
            while (inputStream.read(buffer).also { len = it } > 0) {
                outputStream.write(buffer, 0, len)
                total += len
            }
        }
        outputStream.flush()
        outputStream.close()
        inputStream?.close()

        Log.i("ImagePickerUtils", "Saved uri=$uri to file=${file.absolutePath}, size=$total, mime=$mimeType, originalName=$originalName")

        if (file.length() == 0L) {
            Log.w("ImagePickerUtils", "Created file has 0 size, returning null")
            return null
        }

        file
    } catch (e: Exception) {
        Log.e("ImagePickerUtils", "uriToFile error", e)
        null
    }
}

/**
 * Convert the input file to a JPEG file in cache and return it. If input is already JPEG, returns the original file.
 */
fun convertFileToJpeg(input: File, context: Context, quality: Int = 90): File? {
    return try {
        // quick check by extension
        val ext = input.name.substringAfterLast('.', "").lowercase()
        if (ext == "jpg" || ext == "jpeg") return input

        // decode bitmap (avoid loading very large bitmaps fully if needed)
        val options = BitmapFactory.Options().apply { inPreferredConfig = Bitmap.Config.ARGB_8888 }
        val bitmap: Bitmap = BitmapFactory.decodeFile(input.absolutePath, options) ?: return null

        val outFile = File(context.cacheDir, "converted_${System.currentTimeMillis()}.jpg")
        FileOutputStream(outFile).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
            fos.flush()
        }

        Log.i("ImagePickerUtils", "Converted file ${input.name} -> ${outFile.name}, size=${outFile.length()}")
        outFile
    } catch (e: Exception) {
        Log.e("ImagePickerUtils", "convertFileToJpeg error", e)
        null
    }
}
