package com.biathlonapp.data.model

import java.io.Serializable

sealed class TeamItem {
    data class Header(val title: String) : TeamItem()
    data class Category(
        val id: Int,
        val title: String,
        val gender: String,
        val teamType: String
    ) : TeamItem(), Serializable
}