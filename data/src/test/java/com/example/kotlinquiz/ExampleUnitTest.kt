package com.example.kotlinquiz

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.io.IOException


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val file = File("question.json")
        val filePath = file.absolutePath
        println(" my fooot "+filePath)
        println(" my fooot "+getAbsolutePath("/"))
        assertEquals(4, 2 + 2)
    }

    @Throws(IOException::class)
    fun getAbsolutePath(relativeFilePath: String?): String? {
        val url = this.javaClass.getResource(relativeFilePath)
        return url.path
    }
}