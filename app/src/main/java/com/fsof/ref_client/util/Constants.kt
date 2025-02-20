package com.fsof.ref_client.util

object APIs {
    const val BASE_URL: String = "https://port-0-ref-server-1mrfs72llwxvw3wk.sel5.cloudtype.app/" // "http://10.0.2.2:8000/"
    const val TIMEOUT = 30L
    const val NUTRIENTS: String = "nutrients"
    const val RECIPES: String = "recipes"
    const val FOODS: String = "foods"
}

object BuildConfig {
    const val APPLICATION_ID: String = "com.fsof.ref_client"
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