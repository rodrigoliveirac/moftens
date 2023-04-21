package com.rodcollab.moftens.player.recentlyPlayedTracks.ui.view.observer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.rodcollab.moftens.player.recentlyPlayedTracks.ui.view.RecentlyPlayedTracksViewModel

class RecentlyPlayedTracksObserver(private val viewModel: RecentlyPlayedTracksViewModel) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }
}
