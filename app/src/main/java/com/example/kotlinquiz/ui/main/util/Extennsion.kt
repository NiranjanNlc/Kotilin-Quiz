package com.example.kotlinquiz.ui.main.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader


val fileName = "question.json"



fun readJsonFileFromAssets(context: Context): String {
  // read json file from assets
    println(context)
    val inputStream = context.assets.open(fileName)
    return BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
}



