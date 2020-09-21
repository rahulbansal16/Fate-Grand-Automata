package com.mathewsachin.fategrandautomata.util

import android.os.Environment
import com.mathewsachin.fategrandautomata.scripts.IDropScreenshotStore
import com.mathewsachin.libautomata.IPattern
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class LegacyDropScreenshotStore : IDropScreenshotStore {
    companion object {
        const val subFolder = "FGA/drops"
    }

    private val dropsFolder by lazy {
        val folder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            subFolder
        )

        if (!folder.exists()) {
            folder.mkdirs()
        }

        folder
    }

    override fun insert(images: List<IPattern>) {
        val sdf = SimpleDateFormat("dd-M-yyyy-hh-mm-ss", Locale.US)
        val timeString = sdf.format(Date())

        for ((i, image) in images.withIndex()) {
            val dropFileName = "${timeString}.${i}.png"

            image.use {
                File(dropsFolder, dropFileName).outputStream().use { stream ->
                    image.save(stream)
                }
            }
        }
    }
}