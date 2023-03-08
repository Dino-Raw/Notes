package com.example.notes.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter
import com.example.notes.R


object ViewBindingAdapters {

    private val gradientList: ArrayList<IntArray> = arrayListOf (
        intArrayOf(R.color.white, R.color.white),
        intArrayOf(R.color.roseanna_start, R.color.roseanna_end),
        intArrayOf(R.color.blue_start, R.color.blue_end),
        intArrayOf(R.color.summer_dog_start, R.color.summer_dog_end),
        intArrayOf(R.color.snapchat_start, R.color.snapchat_end),
        intArrayOf(R.color.vice_city_start, R.color.vice_city_end),
        intArrayOf(R.color.piggy_pink_start, R.color.piggy_pink_end),
        intArrayOf(R.color.maldives_start, R.color.maldives_end),
    )

    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    @BindingAdapter("gradient")
    fun setBackgroundColors(view: View, gradientNum: Int) {
        view.background = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                view.context.getColor(gradientList[gradientNum][0]),
                view.context.getColor(gradientList[gradientNum][1]),
            )
        ).also {
            it.cornerRadius = 15f
        }

    }
}