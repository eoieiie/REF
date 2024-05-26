package com.fsof.project.utils
// Secret.kt 로 변경 예정
object APIs {
    const val BASE_URL: String = "http://10.0.2.2:8000/" // 배포 후 수정
    const val NUTRIENTS: String = "nutrients" // http://localhost:8000/nutrients
    const val RECIPES: String = "recipes" // http://localhost:8000/recipes
}

object BuildConfig {
    const val APPLICATION_ID: String = "com.fsof.project"
    const val DEBUG: Boolean = true
    const val BUILD_TYPE: String = "debug"
    const val VERSION_CODE: Int = 1
    const val VERSION_NAME: String = "1.0"
}