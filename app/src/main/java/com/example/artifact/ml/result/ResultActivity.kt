package com.example.artifact.ml.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.artifact.data.response.AnalysisResults
import com.example.artifact.data.response.GetAllDataResponseItem
import com.example.artifact.data.response.MLResponse
import com.example.artifact.databinding.ActivityResultBinding
import com.example.artifact.di.Injection
import com.example.artifact.view.ui.MainActivity
import com.google.android.material.snackbar.Snackbar

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    companion object {
        const val HISTORY_DETAIL_ID = "history_detail_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getParcelableExtra<GetAllDataResponseItem>(HISTORY_DETAIL_ID)

        binding.tvResultSimilarity.text = result?.imageTitle.toString()

        binding.backBase.setOnClickListener {
            finish()
        }

        Glide.with(this)
            .load(result!!.urlPhoto)
            .into(binding.ivResultImage)
        binding.tvResultDetection.text = HtmlCompat.fromHtml(
            result!!.name.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

//        viewModel.isBookmarked(storyId).observe(this) { favoriteEvent ->
//            if (favoriteEvent == null) {
//                binding.ivItemBookmark.setImageResource(R.drawable.baseline_favorite_border_24)
//            } else {
//                binding.ivItemBookmark.setImageResource(R.drawable.baseline_favorite_24)
//            }
//
//            binding.ivItemBookmark.setOnClickListener {
//                viewModel.toggleFavorite(currentEvent, favoriteEvent != null)
//            }

    }
}