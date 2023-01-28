package com.example.domain.model

import java.io.Serializable

data class NoteDomain (
    var id: Int?,
    var title: String,
    var description: String,
    var pinned: Boolean,
    var color: Int,
) : Serializable