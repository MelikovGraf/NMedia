package ru.netology.nmedia.ui.intent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.IntentVideoBinding

class intentVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = IntentVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        if (intent.action != Intent.ACTION_VIEW) return

        val video = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (video.isNullOrBlank()) return

        Snackbar.make(binding.root, video ,BaseTransientBottomBar.LENGTH_INDEFINITE)
            .setAction(android.R.string.ok) { finish() }
            .show()
    }
}