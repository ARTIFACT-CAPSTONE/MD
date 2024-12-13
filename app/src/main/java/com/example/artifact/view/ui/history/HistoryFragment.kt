package com.example.artifact.view.ui.history

import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artifact.R
import com.example.artifact.data.response.GetAllDataResponseItem
import com.example.artifact.databinding.FragmentHistoryBinding
import com.example.artifact.di.Injection
import com.example.artifact.ml.result.ResultActivity
import com.example.artifact.ml.upload.UploadActivity
import com.example.artifact.view.authentication.logout.LogoutActivity

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter
    private var originalList: List<GetAllDataResponseItem> = mutableListOf()
    private var filteredList: List<GetAllDataResponseItem> = mutableListOf()

    private val historyViewModel: HistoryViewModel by viewModels {
        Injection.provideViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fabUpload = binding.fabHistory

        val startColor = ContextCompat.getColor(requireContext(), R.color.gradient_first)
        val middleColor = ContextCompat.getColor(requireContext(), R.color.gradient_middle)
        val endColor = ContextCompat.getColor(requireContext(), R.color.gradient_last)

        fabUpload.post {
            val valueAnimator = ValueAnimator.ofArgb(startColor, middleColor, endColor).apply {
                duration = 4000
                setEvaluator(androidx.vectordrawable.graphics.drawable.ArgbEvaluator())
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE

                addUpdateListener { animator ->
                    val color = animator.animatedValue as Int
                    fabUpload.backgroundTintList = ColorStateList.valueOf(color)
                }
            }
            valueAnimator.start()
        }

        fabUpload.setOnClickListener {
            val intent = Intent(requireContext(), UploadActivity::class.java)
            startActivity(intent)
        }

        historyViewModel.getStories()
        setupRecyclerView()
        observeViewModel()
        setupSearchBar()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)

        historyAdapter = HistoryAdapter()
        historyAdapter.setOnItemClickListener { historyId ->
            val intent = Intent(requireContext(), ResultActivity::class.java)
            intent.putExtra(ResultActivity.HISTORY_DETAIL_ID, historyId)
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity()).toBundle())
        }
        binding.rvHistory.adapter = historyAdapter
    }

    private fun setupSearchBar() {
        with(binding) {
            svHistory.setupWithSearchBar(sbHistory)
            svHistory.editText.setOnEditorActionListener { _, _, _ ->
                val query = svHistory.text.toString().lowercase()
                filteredList = originalList.filter { it.name?.lowercase()?.contains(query) == true }
                setStoryData(filteredList)
                svHistory.hide()
                false
            }
        }
    }

    private fun observeViewModel() {
        historyViewModel.listStory.observe(viewLifecycleOwner) { stories ->
            originalList = stories
            filteredList = stories
            setStoryData(filteredList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                val intent = Intent(requireContext(), LogoutActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setStoryData(stories: List<GetAllDataResponseItem>) {
        historyAdapter.submitList(stories.sortedByDescending { it.id })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        historyViewModel.getStories()
    }
}