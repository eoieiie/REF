package com.fsof.project.utils

object APIs {
    const val BASE_URL: String = "http://10.0.2.2:8000/" // 배포 후 수정
    const val TIMEOUT = 30L // 30초
    const val NUTRIENTS: String = "nutrients"
    const val RECIPES: String = "recipes"
}

object BuildConfig {
    const val APPLICATION_ID: String = "com.fsof.project"
    const val DEBUG: Boolean = true
    const val BUILD_TYPE: String = "debug"
    const val VERSION_CODE: Int = 1
    const val VERSION_NAME: String = "1.0"
}

object TFLite {
    const val IMAGENET_CLASSIFY_MODEL = "model_unquant.tflite"
    const val LABEL_FILE = "labels.txt"
}

object Alarm {
    const val NOTIFICATION_ID = 0
    const val CHANNEL_ID = "channel_id"
    const val CHANNEL_NAME = "ChannelName"
}