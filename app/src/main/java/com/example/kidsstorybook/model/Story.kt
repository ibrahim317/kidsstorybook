package com.example.kidsstorybook.model

data class Story(
    val id: Int,
    val title: String,
    val text: String,
    val content: String,
    val arabicContent: String,
    val turkishContent: String,
    val imageResId: Int,
    val availableLanguages: List<String>,
    val isFavorite: Boolean = false
) 