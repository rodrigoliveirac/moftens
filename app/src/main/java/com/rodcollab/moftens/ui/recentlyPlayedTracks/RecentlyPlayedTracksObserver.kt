package com.rodcollab.moftens.ui.recentlyPlayedTracks

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class RecentlyPlayedTracksObserver(private val viewModel: RecentlyPlayedTracksViewModel) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }
}
