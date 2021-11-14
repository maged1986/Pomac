package com.example.pomacapplication.models

import java.io.Serializable

data class Result(
    val `abstract`: String,
    val byline: String,
    val created_date: String,
    val item_type: String,
    val multimedia: List<Multimedia>,
    val published_date: String,
    val section: String,
    val title: String,
    val updated_date: String,
):Serializable