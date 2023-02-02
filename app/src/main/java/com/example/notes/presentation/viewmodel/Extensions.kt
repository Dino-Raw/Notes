package com.example.notes.presentation.viewmodel

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.mutate(mutator: T.() -> Unit) {
    this.value = this.value?.apply(mutator)
}
