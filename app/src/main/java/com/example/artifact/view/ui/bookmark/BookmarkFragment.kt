package com.example.artifact.view.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artifact.databinding.FragmentBookmarkBinding
import com.example.artifact.di.Injection
import com.example.artifact.ml.upload.UploadActivity
import com.example.artifact.view.ui.history.BookmarkViewModel


class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookmarkAdapter: BookmarkAdapter
    private val bookmarkViewModel: BookmarkViewModel by viewModels {
        Injection.provideViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeBookmarks()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvBookmark.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvBookmark.addItemDecoration(itemDecoration)

        bookmarkAdapter.setOnItemClickListener { itemId ->
            val intent = Intent(requireContext(), UploadActivity::class.java)
            startActivity(intent)
        }

        binding.rvBookmark.adapter = bookmarkAdapter
    }

    private fun observeBookmarks() {
        bookmarkViewModel.allBookmarks.observe(viewLifecycleOwner) { bookmarks ->
            bookmarkAdapter.submitList(bookmarks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
