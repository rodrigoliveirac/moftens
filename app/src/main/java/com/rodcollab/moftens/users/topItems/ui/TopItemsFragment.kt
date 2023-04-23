package com.rodcollab.moftens.users.topItems.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodcollab.moftens.databinding.FragmentTopItemsBinding
import com.rodcollab.moftens.users.topItems.ui.adapter.TopItemsAdapter
import com.rodcollab.moftens.users.topItems.viewmodel.TopItemsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopItemsFragment : Fragment() {

    private var _binding: FragmentTopItemsBinding? = null
    private val binding: FragmentTopItemsBinding get() = _binding!!

    private lateinit var adapter: TopItemsAdapter

    private lateinit var viewModel: TopItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TopItemsViewModel::class.java]
        lifecycle.addObserver(TopItemsObserver(viewModel))
        adapter = TopItemsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.streamAndOnce().observe(viewLifecycleOwner) { uiState ->
            bindUiState(uiState)
        }
    }

    private fun bindUiState(uiState: TopItemsViewModel.UiState) {
        adapter.submitList(uiState.list)
    }
}