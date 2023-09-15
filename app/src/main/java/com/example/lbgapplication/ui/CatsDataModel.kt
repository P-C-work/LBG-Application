package com.example.lbgapplication.ui

data class CatsDataModel(
    val breeds: List<Breed>,
    val categories: List<Category>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)