package com.rodcollab.moftens.users.topItems.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.rodcollab.moftens.users.topItems.viewmodel.TopItemsViewModel

class TopItemsObserver(private val viewModel: TopItemsViewModel) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }

}
