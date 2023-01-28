package com.example.notes.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager

@BindingAdapter("android:animated_visibility")
fun setAnimatedVisibility(target: View, visibility: Boolean) {
    val transition: Transition = Slide().apply {
        duration = 700L
        addTarget(target)
    }

    TransitionManager.beginDelayedTransition(target.rootView as ViewGroup, transition)
    target.visibility = if (visibility) View.VISIBLE else View.GONE
}
