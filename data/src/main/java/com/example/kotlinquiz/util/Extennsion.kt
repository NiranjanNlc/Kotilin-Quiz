package com.example.kotlinquiz.util

import android.content.Context
import com.example.data.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader




fun readJsonFileFromAssets(context: Context): String {
    println(context)
    val inputStream = context.resources.openRawResource( R.raw.question)
    return BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
}


