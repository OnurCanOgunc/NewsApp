package com.decode.composenews.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

fun Context.shareNews(newsTitle: String, newsLink: String) {
    val shareContent =
        "Check out this news:\n\nTitle: $newsTitle\n\nRead more at: $newsLink"
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, shareContent)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    try {
        startActivity(shareIntent)
    } catch (e: Exception) {
        Toast.makeText(this, "Failed to share news!", Toast.LENGTH_SHORT).show()
    }
}

fun Context.openLink(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent, null)
}