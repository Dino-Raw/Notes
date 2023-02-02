package com.example.notes.presentation.fragment

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

fun View.visibleWithAnimation(anim: Int) {
    val animation = AnimationUtils.loadAnimation(this@visibleWithAnimation.context, anim)
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) {
            this@visibleWithAnimation.visibility = View.VISIBLE
        }

        override fun onAnimationRepeat(animation: Animation) {
        }
    })
    this@visibleWithAnimation.startAnimation(animation)
}

fun View.goneWithAnimation(anim: Int) {
    val animation: Animation =
        AnimationUtils.loadAnimation(this@goneWithAnimation.context, anim)
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            this@goneWithAnimation.visibility = View.GONE
        }

        override fun onAnimationEnd(animation: Animation) {
        }

        override fun onAnimationRepeat(animation: Animation) {
        }
    })
    this@goneWithAnimation.startAnimation(animation)
}