package com.rodcollab.moftens.users.topItems.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodcollab.moftens.databinding.FragmentTopItemsBinding
import com.rodcollab.moftens.users.topItems.model.TopItemElement
import com.rodcollab.moftens.users.topItems.ui.adapter.TopItemsAdapter

class TopItemsFragment : Fragment() {

    private var _binding: FragmentTopItemsBinding? = null
    private val binding: FragmentTopItemsBinding get() = _binding!!

    private lateinit var adapter: TopItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        adapter.submitList(
            listOf(
                TopItemElement(
                    id = "id",
                    artistName = "Rodrigo"
                )
            )
        )

    }
}