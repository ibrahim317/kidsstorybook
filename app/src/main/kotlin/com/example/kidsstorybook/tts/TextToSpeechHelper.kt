package com.example.kidsstorybook.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*

class TextToSpeechHelper(private val context: Context) {
    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Default language is English
                setLanguage("en")
            }
        }
    }

    fun setLanguage(languageCode: String) {
        val locale = when (languageCode) {
            "ar" -> Locale("ar")
            "tr" -> Locale("tr")
            "en" -> Locale.US
            "hi" -> Locale("hi")
            "sq" -> Locale("sq")
            "da" -> Locale("da")
            else -> Locale.US
        }
        textToSpeech?.language = locale
    }

    fun speak(text: String, utteranceId: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun shutdown() {
        textToSpeech?.shutdown()
    }
} 