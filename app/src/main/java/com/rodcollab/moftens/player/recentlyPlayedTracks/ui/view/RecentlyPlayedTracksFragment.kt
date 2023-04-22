package com.rodcollab.moftens.player.recentlyPlayedTracks.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.rodcollab.moftens.R
import com.rodcollab.moftens.databinding.FragmentHomeBinding
import com.rodcollab.moftens.player.recentlyPlayedTracks.ui.view.adapter.RecentlyTracksAdapter
import com.rodcollab.moftens.player.recentlyPlayedTracks.ui.view.observer.RecentlyPlayedTracksObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentlyPlayedTracksFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecentlyTracksAdapter

    private lateinit var viewModel: RecentlyPlayedTracksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecentlyPlayedTracksViewModel::class.java]
        lifecycle.addObserver(RecentlyPlayedTracksObserver(viewModel))
        adapter = RecentlyTracksAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
    }

    private fun setupAdapter() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        addingDividerDecoration()

        viewModel.streamAndOnce().observe(viewLifecycleOwner) {
            adapter.submitList(it.list)
        }
    }

    private fun addingDividerDecoration() {
        // Adding Line between items with MaterialDividerItemDecoration
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)

        // Adding the line at the end of the list
        divider.isLastItemDecorated = true

        val resources = requireContext().resources

        // Adding start spacing
        divider.dividerInsetStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

        // Defining size of the line
        divider.dividerThickness = resources.getDimensionPixelSize(R.dimen.divider_height)
        divider.dividerColor = ContextCompat.getColor(requireContext(), com.google.android.material.R.color.primary_dark_material_light)

        binding.recyclerView.addItemDecoration(divider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


